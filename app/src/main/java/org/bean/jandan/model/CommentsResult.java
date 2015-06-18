package org.bean.jandan.model;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public abstract class CommentsResult<E, R extends Comment> extends PageResult<R> {

    private ArrayList<E> comments;

    public ArrayList<E> getComments() {
        return comments;
    }

    public void setComments(ArrayList<E> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "CommentsResult{" +
                "comments=" + comments +
                "} " + super.toString();
    }
}
