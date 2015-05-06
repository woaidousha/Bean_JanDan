package org.bean.jandan.model;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class PostsResult extends PageResult {

    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public List<Post> getResults() {
        return posts;
    }
}
