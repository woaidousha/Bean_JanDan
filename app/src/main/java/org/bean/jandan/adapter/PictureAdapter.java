package org.bean.jandan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.adapter.vh.PictureRViewHolder;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class PictureAdapter extends CommonRecycleAdapter<SinglePicture> {

    public PictureAdapter(Context context, AdapterDataSource dataSource) {
        super(context, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new PictureRViewHolder(LayoutInflater.from(getContext())
                                                    .inflate(R.layout.list_picture_item,
                                                            viewGroup, false));
    }
}
