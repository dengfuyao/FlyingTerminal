package com.flyingogo.flyingterminal.view;

/**
 * 作者：dfy on 10/7/2017 13:59
 * <p> 骑行记录的view
 * 邮箱：dengfuyao@163.com
 */

public interface RecordView {
    void onLoadDataFailed();  //加载数据成功

    void onLoadDataSuccess();  //加载数据失败;

    void onLoadDone();      //没有更多数据;
}
