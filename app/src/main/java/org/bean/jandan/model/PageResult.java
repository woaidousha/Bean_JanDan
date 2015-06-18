package org.bean.jandan.model;

import org.bean.jandan.common.cache.Cacheable;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public abstract class PageResult<T extends Cacheable> extends Result<T> {

    private int current_page;
    private int total_comments;
    private int page_count;
    private int count;
    private int count_total;
    private int pages;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getTotal_comments() {
        return total_comments + count_total;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public int getPage_count() {
        return page_count + pages;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount_total(int count_total) {
        this.count_total = count_total;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "current_page=" + current_page +
                ", total_comments=" + total_comments +
                ", page_count=" + page_count +
                ", count=" + count +
                ", count_total=" + count_total +
                ", pages=" + pages +
                "} " + super.toString();
    }
}
