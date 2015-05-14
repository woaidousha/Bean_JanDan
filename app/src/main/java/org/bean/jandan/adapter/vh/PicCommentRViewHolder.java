package org.bean.jandan.adapter.vh;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.FetchImageHelper;
import org.bean.jandan.model.PicComment;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PicCommentRViewHolder extends RViewHolder<PicComment> {

    private TextView mAuthor;
    private TextView mDate;
    private TextView mMessage;
    private SimpleDraweeView mAvatar;

    public PicCommentRViewHolder(View mView) {
        super(mView);
    }

    @Override
    public void inflate(View view) {
        mAuthor = (TextView) view.findViewById(R.id.author);
        mDate = (TextView) view.findViewById(R.id.date);
        mMessage = (TextView) view.findViewById(R.id.message);
        mAvatar = (SimpleDraweeView) view.findViewById(R.id.author_avatar);
    }

    @Override
    public void refresh(PicComment item) {
        super.refresh(item);
        mAuthor.setText(item.getAuthor().getName());
        mDate.setText(item.getCreated_at());
        mMessage.setText(item.getMessage());
        String thumbUrl = item.getAuthor().getAvatar_url();
        if (!TextUtils.isEmpty(thumbUrl)) {
            FetchImageHelper.fetchRecyclerViewImage(mAvatar, Uri.parse(thumbUrl));
        }
    }

}
