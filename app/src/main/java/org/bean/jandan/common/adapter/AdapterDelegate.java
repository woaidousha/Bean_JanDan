package org.bean.jandan.common.adapter;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public interface AdapterDelegate {
    public int getViewTypeCount();

    public Class<? extends ViewHolder> viewHolderAtPosition(int position);

    public boolean enabled(int position);
}
