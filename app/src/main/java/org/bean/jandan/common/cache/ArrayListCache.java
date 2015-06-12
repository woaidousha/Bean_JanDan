package org.bean.jandan.common.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public class ArrayListCache extends Cache<String, List<Cacheable>> {

    public void appendTo(String key, List<Cacheable> values) {
        List<Cacheable> old = get(key);
        if (old == null) {
            old = new ArrayList<>();
        } else {
            old = merge(old, values);
        }
        old.addAll(0, values);
        put(key, old);
    }

    public void append(String key, List<Cacheable> values) {
        List<Cacheable> old = get(key);
        if (old == null) {
            old = new ArrayList<>();
        } else {
            old = merge(old, values);
        }
        old.addAll(values);
        put(key, old);
    }

    private List<Cacheable> merge(List<Cacheable> old, List<Cacheable> values) {
        for (Cacheable cacheable : values) {
            old.remove(cacheable);
        }
        return old;
    }
}
