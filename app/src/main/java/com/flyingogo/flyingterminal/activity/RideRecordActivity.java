package com.flyingogo.flyingterminal.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.adapter.MyRecyclerViewAdapter;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.module.RecordBean;
import com.flyingogo.flyingterminal.presenter.RecordPresenter;
import com.flyingogo.flyingterminal.presenter.RecordPresenterImp;
import com.flyingogo.flyingterminal.view.RecordView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 7/7/2017 10:40
 * <p>  骑行记录:
 * 邮箱：dengfuyao@163.com
 */

public class RideRecordActivity extends BaseActivity implements RecordView{
    @BindView(R.id.title)
    TextView           mTitle;
    @BindView(R.id.right)
    TextView           mRight;
    @BindView(R.id.rl_title)
    RelativeLayout     mRlTitle;
    @BindView(R.id.recycleView)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefresh;
    private String                                 mCard_no;
    private List<RecordBean.DataBean.DataListBean> mBeanList;
    private MyRecyclerViewAdapter                  mAdapter;


    private RecordPresenter mPresenter;

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_ride_record;
    }

    @Override
    protected void onInit() {
        super.onInit();
        Intent intent = getIntent();
        mCard_no = intent.getStringExtra(Contants.CARD_NO);
        mPresenter = new RecordPresenterImp(mContext,this,mCard_no);
        mBeanList = mPresenter.getItemBeanList();
        initSwipeRef();
        initRecycleV();
        initLoad();
        //loadData(intent);
    }

    private void initLoad() {
        if (mBeanList!=null){
            mBeanList.clear();
        }
        mPresenter.onLoadDataList();
    }

    private void initSwipeRef() {
        mSwipeRefresh.setColorSchemeResources(R.color.green, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(mRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.e(TAG, "onRefresh: 下拉刷新" );
            mPresenter.onRefresh();
            mAdapter.notifyDataSetChanged();

        }
    };
    private void initRecycleV() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyRecyclerViewAdapter(mContext, mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mScrollListener);

    }
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE){  //
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int position = layoutManager.findLastVisibleItemPosition();
                Log.e(TAG, "onScrollStateChanged: 加载数据前一个item = "+position );
                if (position == mPresenter.getItemBeanList().size()-1){
                    mPresenter.onLoadMoreData();
                    mAdapter.notifyDataSetChanged();
                }
            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    private static final String TAG = "RideRecordActivity";

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }


    //加载数据成功的回调;
    @Override
    public void onLoadDataFailed() {
        mSwipeRefresh.setRefreshing(false);
        Toast.makeText(mContext, "加载数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadDataSuccess() {
        Log.e(TAG, "onLoadDataSuccess: 加载数据成功  ="+mBeanList.size() );
        mSwipeRefresh.setRefreshing(false);  //刷新成功后停止刷新;
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadDone() {
        Toast.makeText(mContext, "别拉了,已经到底了", Toast.LENGTH_SHORT).show();
    }
}
