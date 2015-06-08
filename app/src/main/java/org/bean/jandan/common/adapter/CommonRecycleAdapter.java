package org.bean.jandan.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.bean.jandan.adapter.vh.RViewHolder;
import org.bean.jandan.common.content.DataSource;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    private DataSource mDataSource;

    public CommonRecycleAdapter(Context context, AdapterDataSource dataSource) {
        this.mContext = context;
        dataSource.setAdapter(this);
        this.mDataSource = dataSource;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        T t = getItem(position);
        if (t == null) {
            return;
        }
        ((RViewHolder) viewHolder).refresh(t);
    }

    public T getItem(int position) {
        if (mDataSource.getData() == null || position >= mDataSource.getData().size()) {
            return null;
        }
        return (T) mDataSource.getData().get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSource.getData() == null ? 0 : mDataSource.getData().size();
    }
}
