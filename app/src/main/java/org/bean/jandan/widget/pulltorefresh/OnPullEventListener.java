package org.bean.jandan.widget.pulltorefresh;

import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public interface OnPullEventListener<T extends View> {

    public void onPullEvent(PullToRefreshBase<T> refreshView, State state);

}
