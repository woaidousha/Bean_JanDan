package org.bean.jandan.adapter.vh;

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
import org.bean.jandan.model.Post;
import org.bean.jandan.widget.drawable.ImageProgressBarDrawable;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class PostRViewHolder extends RViewHolder {

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

    public void refresh(Object item) {
        Post post = (Post) item;
        mAuthor.setText(post.getAuthor().getName());
        mDate.setText(post.getDate());
        String commentContent = post.getTitle();
        mCommentContent.setVisibility(TextUtils.isEmpty(commentContent) ? View.GONE : View.VISIBLE);
        mCommentContent.setText(commentContent);
        String thumbUrl = post.getCustom_fields().getFirstThumb();
        if (!TextUtils.isEmpty(thumbUrl)) {
            ImageProgressBarDrawable drawable;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(thumbUrl))
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
        }
    }

}
