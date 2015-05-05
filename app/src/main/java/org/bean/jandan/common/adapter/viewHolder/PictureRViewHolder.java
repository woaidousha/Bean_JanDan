package org.bean.jandan.common.adapter.viewHolder;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.bean.jandan.R;
import org.bean.jandan.common.util.ShareUtil;
import org.bean.jandan.model.SinglePicture;
import org.bean.jandan.widget.drawable.ImageProgressBarDrawable;

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
        ImageProgressBarDrawable drawable;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(picture.getPicUri())
                                                  .setProgressiveRenderingEnabled(true)
                                                  .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setImageRequest(request)
                                            .setAutoPlayAnimations(true)
                                            .setOldController(mPicture.getController())
                                            .setCallerContext(mPicture.getHierarchy())
                                            .build();
        mPicture.setController(controller);
        if (mPicture.getHierarchy() != null) {
            drawable = new ImageProgressBarDrawable(mResources);
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(mResources)
                    .setFadeDuration(300)
                    .setProgressBarImage(drawable)
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build();
            mPicture.setHierarchy(hierarchy);
        }
        mShare.setOnClickListener(this);
        mShare.setTag(picture.getPicUri());
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.share) {
            Uri uri = (Uri) v.getTag();
            ShareUtil.shareImage(uri);
        }
    }
}
