package org.bean.jandan.fragment;

import org.bean.jandan.adapter.PostsAdapter;
import org.bean.jandan.common.C;
import org.bean.jandan.model.PostsResult;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class NewFreshFragment extends BaseRecycleViewNetFragment<PostsResult> {
    @Override
    protected String url() {
        return C.URL.POSTS;
    }

    @Override
    protected PostsAdapter configAdapter() {
        return new PostsAdapter(getActivity(), getDataSource());
    }



}
