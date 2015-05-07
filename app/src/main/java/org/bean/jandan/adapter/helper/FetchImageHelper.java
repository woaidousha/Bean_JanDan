package org.bean.jandan.adapter.helper;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.bean.jandan.BeanApp;
import org.bean.jandan.common.util.NetworkUtil;
import org.bean.jandan.widget.drawable.ImageProgressBarDrawable;

/**
 * Created by liuyulong@yixin.im on 2015/5/7.
 */
public class FetchImageHelper {

    public static void fetchRecyclerViewImage(SimpleDraweeView view, Uri uri) {
        if (view == null) {
            return;
        }

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                                                  .setProgressiveRenderingEnabled(true)
                                                  .setLowestPermittedRequestLevel(getLowestLevel())
                                                  .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setImageRequest(request)
                                            .setAutoPlayAnimations(true)
                                            .setOldController(view.getController())
                                            .build();
        view.setController(controller);
        if (view.getHierarchy() != null) {
            ImageProgressBarDrawable drawable = new ImageProgressBarDrawable(BeanApp.getApp().getResources());
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(BeanApp.getApp().getResources())
                    .setFadeDuration(300)
                    .setProgressBarImage(drawable)
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build();
            view.setHierarchy(hierarchy);
        }
    }

    private static ImageRequest.RequestLevel getLowestLevel() {
        if (!NetworkUtil.isWifi(BeanApp.getApp())) {
            return ImageRequest.RequestLevel.DISK_CACHE;
        }
        return ImageRequest.RequestLevel.FULL_FETCH;
    }

}
