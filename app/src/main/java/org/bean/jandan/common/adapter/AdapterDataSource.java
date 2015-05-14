package org.bean.jandan.common.adapter;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public interface AdapterDataSource<T> {

    public boolean addAll(int index, List<T> t);
    public boolean addAll(List<T> t);
    public boolean remove(T t);
    public T remove(int index);
    public void notifyDataSetChanged();
}
