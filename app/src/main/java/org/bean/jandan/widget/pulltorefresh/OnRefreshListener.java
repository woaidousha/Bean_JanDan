package org.bean.jandan.widget.pulltorefresh;

import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public interface OnRefreshListener<T extends View> {

    public void onPullUpToRefresh(PullToRefreshBase refreshView);

    public void onPullDownToRefresh(PullToRefreshBase refreshView);

}
