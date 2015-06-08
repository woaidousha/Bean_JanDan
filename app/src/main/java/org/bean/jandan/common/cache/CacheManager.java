package org.bean.jandan.common.cache;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public class CacheManager {

    private static ArrayListCache mCache = new ArrayListCache();

    private static final CacheManager sInstance = new CacheManager();

    public static CacheManager get() {
        return sInstance;
    }

    public ArrayListCache cache() {
        return mCache;
    }
}
