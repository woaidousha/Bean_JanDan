package org.bean.jandan.common.cache;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public interface Cacheable<K> {
    public void setBaseKey(K key);
    public K getBaseKey();
}
