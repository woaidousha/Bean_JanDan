package org.bean.jandan.common.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PictureRViewHolder extends RViewHolder {

    private TextView mAuthor;
    private SimpleDraweeView mPicture;
    private TextView mDate;
    private TextView mOO;
    private TextView mXX;

    public PictureRViewHolder(View mView) {
        super(mView);
    }

    @Override
    public void inflate(View view) {
        mAuthor = (TextView) view.findViewById(R.id.author);
        mPicture = (SimpleDraweeView) view.findViewById(R.id.picture);
        mDate = (TextView) view.findViewById(R.id.date);
        mOO = (TextView) view.findViewById(R.id.oo);
        mXX = (TextView) view.findViewById(R.id.xx);
    }

    public void refresh(Object item) {
        SinglePicture picture = (SinglePicture) item;
        mAuthor.setText(picture.getComment_author());
        mDate.setText(picture.getComment_date());
        mOO.setText(picture.getVote_positive());
        mXX.setText(picture.getVote_negative());
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(picture.getPicUri()).setAutoPlayAnimations(true)
                .build();
        mPicture.setController(controller);
    }
}
