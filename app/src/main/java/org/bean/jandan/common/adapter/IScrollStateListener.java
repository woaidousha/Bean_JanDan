package org.bean.jandan.common.adapter;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public interface IScrollStateListener {
    /**
     * move to scrap heap
     */
    public void reclaim();


    /**
     * on idle
     */
    public void onImmutable();
}
