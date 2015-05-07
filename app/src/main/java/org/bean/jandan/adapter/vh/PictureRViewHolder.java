package org.bean.jandan.adapter.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.FetchImageHelper;
import org.bean.jandan.common.util.ShareUtil;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PictureRViewHolder extends RViewHolder implements View.OnClickListener {

    private TextView mAuthor;
    private SimpleDraweeView mPicture;
    private TextView mDate;
    private TextView mOO;
    private TextView mXX;
    private TextView mShare;
    private TextView mCommentContent;

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
        mShare = (TextView) view.findViewById(R.id.share);
        mCommentContent = (TextView) view.findViewById(R.id.comment_content);
    }

    public void refresh(Object item) {
        SinglePicture picture = (SinglePicture) item;
        mAuthor.setText(picture.getComment_author());
        mDate.setText(picture.getComment_date());
        mOO.setText(String.format(mResources.getString(R.string.button_text_oo), picture.getVote_positive()));
        mXX.setText(String.format(mResources.getString(R.string.button_text_xx), picture.getVote_negative()));
        String commentContent = picture.getText_content();
        mCommentContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mCommentContent.setText(commentContent);
        FetchImageHelper.fetchRecyclerViewImage(mPicture, picture.getPicUri());
        mShare.setOnClickListener(this);
        mShare.setTag(picture);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.share) {
            SinglePicture picture = (SinglePicture) v.getTag();
            ShareUtil.shareImage(picture);
        }
    }
}
