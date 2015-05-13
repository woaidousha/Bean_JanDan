package org.bean.jandan.common.page;

import android.text.TextUtils;

import org.bean.jandan.common.C;
import org.bean.jandan.common.util.URLUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class Page {

    private String baseUrl;

    private int currentPage;
    private int pageSize;
    private int totalPage = Integer.MAX_VALUE;
    private int totalSize;

    public Page(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

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

    public String nextPage() {
        if (currentPage == totalPage) {
            return null;
        }
        next();
        return pageUrl(baseUrl, getCurrentPage());
    }

    public String firstPage() {
        return pageUrl(baseUrl, C.URL.FIRST_PAGE);
    }

    private String pageUrl(String baseUrl, int page) {
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&");
        builder.append(C.URL.PARAM_PAGE);
        builder.append("=");
        builder.append(page);
        return builder.toString();
    }

    public boolean isFirstPage(String url) {
        Map<String, String> values = new HashMap<>();
        URLUtil.decode(url, values);
        try {
            int page = Integer.parseInt(values.get(C.URL.PARAM_PAGE));
            return page == C.URL.FIRST_PAGE;
        } catch (Exception e) {
            return false;
        }
    }
}
