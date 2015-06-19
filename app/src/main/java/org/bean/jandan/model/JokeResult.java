package org.bean.jandan.model;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class JokeResult extends CommentsResult<Comment, Comment> {

    @Override
    public List<Comment> transfor() {
        return getComments();
    }
}
