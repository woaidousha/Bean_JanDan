package org.bean.jandan.adapter.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.activity.CommentsActivity;
import org.bean.jandan.activity.PictureActivity;
import org.bean.jandan.adapter.helper.FetchImageHelper;
import org.bean.jandan.common.util.ShareUtil;
import org.bean.jandan.model.SinglePicture;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PictureRViewHolder extends RViewHolder<SinglePicture> implements View.OnClickListener {

    private TextView mAuthor;
    private SimpleDraweeView mPicture;
    private TextView mDate;
    private TextView mOO;
    private TextView mXX;
    private TextView mShare;
    private TextView mCommentContent;
    private TextView mComments;

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
        mComments = (TextView) view.findViewById(R.id.comments);
        mPicture.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mComments.setOnClickListener(this);
    }

    @Override
    public void refresh(SinglePicture item) {
        super.refresh(item);
        mAuthor.setText(item.getComment_author());
        mDate.setText(item.getComment_date());
        mOO.setText(String.format(mResources.getString(R.string.button_text_oo), item.getVote_positive()));
        mXX.setText(String.format(mResources.getString(R.string.button_text_xx), item.getVote_negative()));
        String commentContent = item.getText_content();
        mCommentContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mCommentContent.setText(commentContent);
        FetchImageHelper.fetchRecyclerViewImage(mPicture, item.getPicUri());
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        SinglePicture picture = mData;
        if (viewId == R.id.share) {
            ShareUtil.shareImage(picture);
        } else if (viewId == R.id.picture) {
            PictureActivity.start(mContext, picture.getPicUri());
        } else if (viewId == R.id.comments) {
            CommentsActivity.start(mContext, picture.getComment_ID());
        }
    }
}
