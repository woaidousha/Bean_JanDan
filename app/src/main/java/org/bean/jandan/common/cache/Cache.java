package org.bean.jandan.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public class Cache<K, V> {

    private Map<K, V> mCache = new HashMap<>();

    private Map<K, ArrayList<DataObserver<K>>> mObservers = new HashMap<>();

    public void put(K key, V value) {
        mCache.put(key, value);
        notifyChange(key);
    }

    public V remove(K key) {
        V value = mCache.remove(key);
        if (value != null) {
            notifyChange(key);
        }
        return value;
    }

    public V get(K key) {
        return mCache.get(key);
    }

    public void clear() {
        mCache.clear();
        notifyAllChange();
    }

    public void registerDataObserver(DataObserver<K> observer) {
        if (observer == null) {
            return;
        }
        ArrayList<DataObserver<K>> observers = mObservers.get(observer.getKey());
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.remove(observer);
        observers.add(observer);
    }

    public void unregisterDataObserver(DataObserver<K> observer) {
        ArrayList<DataObserver<K>> observers = mObservers.get(observer.getKey());
        if (observers != null) {
            observers.remove(observer);
        }
    }

    public void notifyChange(K key) {
        ArrayList<DataObserver<K>> observers = mObservers.get(key);
        if (observers != null) {
            for (DataObserver<K> observer : observers) {
                if (observer != null) {
                    observer.onDataChange(key);
                }
            }
        }
    }

    public void notifyAllChange() {
        for (Map.Entry<K, ArrayList<DataObserver<K>>> entry : mObservers.entrySet()) {
            K key = entry.getKey();
            ArrayList<DataObserver<K>> observers = entry.getValue();
            if (observers != null) {
                for (DataObserver<K> observer : observers) {
                    if (observer != null) {
                        observer.onDataChange(key);
                    }
                }
            }
        }
    }
}
