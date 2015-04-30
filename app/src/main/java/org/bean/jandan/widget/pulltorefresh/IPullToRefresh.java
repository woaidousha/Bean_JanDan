package org.bean.jandan.widget.pulltorefresh;

import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public interface IPullToRefresh<T extends View> {

    /**
     * 演示如何使用
     * */
    public boolean demo();

    /**
     * @return 获取加载时显示的view的代理对象，会控制所有加载时布局
     * */
    public ILoadingLayout getLoadingLayoutProxy();

    /**
     * @param includeStart 是否包含下拉时的布局
     * @param includeEnd 是否包含下滑时的布局
     * @return 获取加载时显示的view的代理对象，会控制所有加载时布局
     * */
    public ILoadingLayout getLoadingLayoutProxy(boolean includeStart, boolean includeEnd);

    /**
     * @return 返回可以刷新的view
     * */
    public T getRefreshableView();

    /**
     * @return 返回当前状态
     * */
    public State getState();

    /**
     * @return 是否正在刷新
     * */
    public boolean isRefreshing();

    /**
     * 刷新成功后调用
     * */
    public void onRefreshComplete();


    /**
     * @param listener 监听是否有下拉事件
     * */
    public void setOnPullEventListener(OnPullEventListener listener);

    /**
     * @param listener 监听刷新事件
     * */
    public void setOnRefreshListener(OnRefreshListener listener);

    /**
     * 设置为正在刷新的状态
     * */
    public void setRefreshing();



}
