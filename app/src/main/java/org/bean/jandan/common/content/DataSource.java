package org.bean.jandan.common.content;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/6/8.
 */
public interface DataSource<T> {
    public void notifyDataChanged();
    public List<T> getData();
}
