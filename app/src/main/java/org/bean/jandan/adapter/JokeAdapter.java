package org.bean.jandan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.bean.jandan.R;
import org.bean.jandan.adapter.vh.JokeRViewHolder;
import org.bean.jandan.common.adapter.CommenRecycleAdapter;
import org.bean.jandan.model.Comment;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class JokeAdapter extends CommenRecycleAdapter<Comment> {

    public JokeAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JokeRViewHolder(LayoutInflater.from(getContext())
                                                    .inflate(R.layout.list_joke_item,
                                                            parent, false));
    }
}
