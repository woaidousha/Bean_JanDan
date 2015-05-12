package org.bean.jandan.common.adapter;

import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public interface IViewHolder<T> {
    public void inflate(View view);

    public void refresh(T item);
}
