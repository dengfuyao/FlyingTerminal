package com.flyingogo.flyingterminal.presenter;

import android.content.Context;
import android.util.Log;

import com.flyingogo.flyingterminal.model.RecordBean;
import com.flyingogo.flyingterminal.utils.ThreadUtils;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.flyingogo.flyingterminal.view.RecordView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 作者：dfy on 10/7/2017 13:57
 * <p>  //;骑行记录的数据加载类;
 * 邮箱：dengfuyao@163.com
 */

public class RecordPresenterImp implements RecordPresenter<RecordBean.DataBean.DataListBean> {
    private Context                                mContext;
    private RecordView                             mRecordView;
    private List<RecordBean.DataBean.DataListBean> mListBeen;
    String mCard_no;
    private static final String TAG = "RecordPresenterImp";
    private List<RecordBean> mBenas;

    public RecordPresenterImp(Context context, RecordView recordView, String card_no) {
        mContext = context;
        mRecordView = recordView;
        mListBeen = new ArrayList<>();
        mBenas = new ArrayList<RecordBean>();
        mCard_no = card_no;
    }

    @Override
    public void onLoadDataList() {
        loadDataList(1);
    }

    private void loadDataList(int offSize) {

        Log.e(TAG, "loadDataList: 获取到第 : " + offSize + "页数据");
      //  String url = Contants.URL_RECORD + mCard_no;
        String url =  URLUtils.getRecordCardUrl(String.valueOf(offSize),"10",mCard_no);
        Log.e(TAG, "loadDataList: 骑行记录的URL ::"+url );
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("请求", "onError: 请求异常" + e.getMessage());
                        ThreadUtils.onRunUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecordView.onLoadDataFailed();
                            }
                        });
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("请求", "onResponse: response = " + response);
                        //  mListBeen.clear();
                        Gson gson = new Gson();
                        //得到请求回来的集合,将集合数据添加到显示数据集;
                        RecordBean recordBean = gson.fromJson(response.toString(), RecordBean.class);

                        mBenas.add(recordBean);
                        List<RecordBean.DataBean.DataListBean> dataList = recordBean.data.dataList;
                      //  Log.e(TAG, "onResponse: 加载的条目数量==" + dataList.size());
                        if (dataList !=null) {
                            mListBeen.addAll(dataList);
                            ThreadUtils.onRunUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRecordView.onLoadDataSuccess();
                                }
                            });
                            Log.e(TAG, "onResponse: datalist.size == " + dataList.size());
                        }else {
                            ThreadUtils.onRunUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRecordView.onLoadDone();
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadDataList(1);
    }

    @Override
    public void onLoadMoreData() {
        loadDataList(mListBeen.size() / 10 + 1);  //加载更多,每次加载后的偏移量;
    }

    @Override
    public List<RecordBean.DataBean.DataListBean> getItemBeanList() {
        return mListBeen;
    }
}
