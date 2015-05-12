package org.bean.jandan.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.bean.jandan.adapter.vh.RViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter implements
        AdapterDataSource<T> {

    private Context mContext;
    private List<T> mDataSource;

    public CommonRecycleAdapter(Context context) {
        this.mContext = context;
        this.mDataSource = new ArrayList<>();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((RViewHolder) viewHolder).refresh(getItem(position));
    }

    public T getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }


    public boolean addAll(int index, List<T> data) {
        return mDataSource.addAll(index, data);
    }

    public boolean addAll(List<T> data) {
        return mDataSource.addAll(data);
    }

    public boolean remove(T t) {
        return mDataSource.remove(t);
    }

    public T remove(int index) {
        return mDataSource.remove(index);
    }
}
