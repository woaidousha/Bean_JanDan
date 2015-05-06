package org.bean.jandan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.CommenRecycleAdapter;
import org.bean.jandan.adapter.vh.PictureRViewHolder;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class PictureAdapter extends CommenRecycleAdapter<SinglePicture> {

    public PictureAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new PictureRViewHolder(LayoutInflater.from(getContext())
                                                    .inflate(R.layout.list_picture_item,
                                                            viewGroup, false));
    }
}
