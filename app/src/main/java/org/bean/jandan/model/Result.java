package org.bean.jandan.model;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public abstract class Result<T> {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract List<T> getResults();
}
