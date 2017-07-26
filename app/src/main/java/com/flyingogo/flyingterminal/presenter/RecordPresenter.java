package com.flyingogo.flyingterminal.presenter;

import java.util.List;

/**
 * 作者：dfy on 10/7/2017 13:50
 * <p>  新车记录 的presenter
 * 邮箱：dengfuyao@163.com
 */

public interface RecordPresenter<T> {
    /**
     * 加载数据;
     */

    void onLoadDataList();

    /**
     * 下拉刷新
     */
    void onRefresh();

    /**
     * 加载更多;
     */
    void onLoadMoreData();

    /**
     * 获取数据集合
     * @return
     */
    List<T> getItemBeanList();
}
