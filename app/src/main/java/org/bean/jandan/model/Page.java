package org.bean.jandan.model;

import android.text.TextUtils;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class Page {

    private int currentPage;
    private int pageSize;
    private int totalPage = Integer.MAX_VALUE;
    private int totalSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    private int next() {
        return ++currentPage;
    }

    private int first() {
        return currentPage = 1;
    }

    public String nextPage(String baseUrl) {
        if (currentPage == totalPage) {
            return null;
        }
        next();
        return pageUrl(baseUrl);
    }

    public String firstPage(String baseUrl) {
        first();
        return pageUrl(baseUrl);
    }

    private String pageUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&");
        builder.append("page=");
        builder.append(getCurrentPage());
        return builder.toString();
    }

    public boolean isFirstPage() {
        return currentPage == 1;
    }
}
