package org.bean.jandan.adapter.vh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.ViewHolder;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class PictureViewHolder extends ViewHolder {

    private TextView mAuthor;
    private ImageView mPicture;
    private TextView mDate;
    private TextView mOO;
    private TextView mXX;

    @Override
    public int getResId() {
        return R.layout.list_picture_item;
    }

    @Override
    public void inflate(View view) {
        mAuthor = (TextView) view.findViewById(R.id.author);
        mPicture = (ImageView) view.findViewById(R.id.picture);
        mDate = (TextView) view.findViewById(R.id.date);
        mOO = (TextView) view.findViewById(R.id.oo);
        mXX = (TextView) view.findViewById(R.id.xx);
    }

    @Override
    public void refresh(Object item) {
        SinglePicture picture = (SinglePicture) item;
        mAuthor.setText(picture.getComment_author());
        mDate.setText(picture.getComment_date());
        mOO.setText(picture.getVote_positive());
        mXX.setText(picture.getVote_negative());
    }
}
