package org.bean.jandan.common.adapter;

import android.support.v7.widget.RecyclerView;

import org.bean.jandan.common.cache.CacheManager;
import org.bean.jandan.common.cache.Cacheable;
import org.bean.jandan.common.content.DataSource;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class AdapterDataSource implements DataSource<Cacheable> {

    private RecyclerView.Adapter mAdapter;
    private String mKey;

    public AdapterDataSource(String key) {
        this.mKey = key;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void notifyDataChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public List<Cacheable> getData() {
        return CacheManager.get().cache().get(mKey);
    }
}
