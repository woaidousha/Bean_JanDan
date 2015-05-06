package org.bean.jandan.adapter.vh;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.bean.jandan.common.adapter.IViewHolder;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class RViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    protected Resources mResources;

    public RViewHolder(View itemView) {
        super(itemView);
        mResources = itemView.getResources();
        inflate(itemView);
    }
}
