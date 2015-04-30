package org.bean.jandan.common.adapter;

import android.support.v7.widget.RecyclerView;

import org.bean.jandan.common.adapter.viewHolder.RViewHolder;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class CommenRecycleAdapter extends RecyclerView.Adapter {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((RViewHolder) viewHolder).refresh(getItem(position));
    }

    public abstract Object getItem(int position);
}
