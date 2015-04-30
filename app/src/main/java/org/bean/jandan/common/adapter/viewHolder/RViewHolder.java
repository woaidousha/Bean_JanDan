package org.bean.jandan.common.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class RViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    public RViewHolder(View itemView) {
        super(itemView);
        inflate(itemView);
    }
}
