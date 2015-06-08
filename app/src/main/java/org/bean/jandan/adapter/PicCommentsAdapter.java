package org.bean.jandan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.bean.jandan.R;
import org.bean.jandan.adapter.vh.PicCommentRViewHolder;
import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.model.PicComment;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class PicCommentsAdapter extends CommonRecycleAdapter<PicComment> {

    public PicCommentsAdapter(Context context, AdapterDataSource dataSource) {
        super(context, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicCommentRViewHolder(LayoutInflater.from(getContext())
                                                    .inflate(R.layout.list_pic_comment_item,
                                                            parent, false));
    }
}
