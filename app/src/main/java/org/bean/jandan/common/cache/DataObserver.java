package org.bean.jandan.common.cache;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public interface DataObserver<K> {
    public void onDataChange(K k);
    public K getKey();
}
