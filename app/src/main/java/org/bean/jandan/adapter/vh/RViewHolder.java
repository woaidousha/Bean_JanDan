package org.bean.jandan.adapter.vh;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.bean.jandan.common.adapter.IViewHolder;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class RViewHolder<T> extends RecyclerView.ViewHolder implements IViewHolder<T> {

    protected Resources mResources;
    protected Context mContext;
    protected T mData;

    public RViewHolder(View itemView) {
        super(itemView);
        mResources = itemView.getResources();
        mContext = itemView.getContext();
        inflate(itemView);
    }

    @Override
    public void refresh(T item) {
        mData = item;
    }
}
