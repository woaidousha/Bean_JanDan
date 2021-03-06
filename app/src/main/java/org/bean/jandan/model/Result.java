package org.bean.jandan.model;

import org.bean.jandan.common.cache.Cacheable;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public abstract class Result<T extends Cacheable> {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract List<T> transfor();

    public List<T> getResult(Object key) {
        List<T> result = transfor();
        if (result != null) {
            for (T t : result) {
                t.setBaseKey(key);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                '}';
    }
}
