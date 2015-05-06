package org.bean.jandan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.bean.jandan.R;
import org.bean.jandan.adapter.vh.PostRViewHolder;
import org.bean.jandan.common.adapter.CommenRecycleAdapter;
import org.bean.jandan.model.Post;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class PostsAdapter extends CommenRecycleAdapter<Post> {

    public PostsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostRViewHolder(LayoutInflater.from(getContext())
                                                    .inflate(R.layout.list_post_item,
                                                            parent, false));
    }
}
