package org.bean.jandan.widget;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public interface LoadListener {

    public void preload();
    public boolean load(boolean head);
    public boolean hasMore();
}
