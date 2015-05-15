package org.bean.jandan.adapter.vh;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.ImageFetchHelper;
import org.bean.jandan.model.Post;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PostRViewHolder extends RViewHolder<Post> {

    private TextView mAuthor;
    private SimpleDraweeView mPicture;
    private TextView mDate;
    private TextView mCommentContent;

    public PostRViewHolder(View mView) {
        super(mView);
    }

    @Override
    public void inflate(View view) {
        mAuthor = (TextView) view.findViewById(R.id.author);
        mPicture = (SimpleDraweeView) view.findViewById(R.id.picture);
        mDate = (TextView) view.findViewById(R.id.date);
        mCommentContent = (TextView) view.findViewById(R.id.comment_content);
    }

    @Override
    public void refresh(Post item) {
        super.refresh(item);
        mAuthor.setText(item.getAuthor().getName());
        mDate.setText(item.getDate());
        String commentContent = item.getTitle();
        mCommentContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mCommentContent.setText(commentContent);
        String thumbUrl = item.getCustom_fields().getFirstThumb();
        if (!TextUtils.isEmpty(thumbUrl)) {
            ImageFetchHelper.fetchRecyclerViewImage(mPicture, Uri.parse(thumbUrl));
        }
    }

}
