package org.bean.jandan.common.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public class ArrayListCache extends Cache<String, List<Cacheable>> {

    public void appendTo(String key, List<Cacheable> values) {
        List<Cacheable> v = get(key);
        if (v == null) {
            v = new ArrayList<>();
        } else {
            merge(v, values);
        }
        v.addAll(0, values);
        put(key, v);
    }

    public void append(String key, List<Cacheable> values) {
        List<Cacheable> v = get(key);
        if (v == null) {
            v = new ArrayList<>();
        } else {
            merge(v, values);
        }
        v.addAll(0, values);
        put(key, v);
    }

    private List<Cacheable> merge(List<Cacheable> old, List<Cacheable> values) {
        for (Cacheable cacheable : values) {
            old.remove(cacheable);
        }
        return old;
    }
}
