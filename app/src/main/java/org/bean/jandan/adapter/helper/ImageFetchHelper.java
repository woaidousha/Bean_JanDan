package org.bean.jandan.adapter.helper;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageView;

import com.facebook.common.executors.HandlerExecutorServiceImpl;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.bean.jandan.BeanApp;
import org.bean.jandan.common.util.NetworkUtil;
import org.bean.jandan.widget.drawable.ImageProgressBarDrawable;

/**
 * Created by liuyulong@yixin.im on 2015/5/7.
 */
public class ImageFetchHelper {

    private static final HandlerThread sBitmapLoadThread;
    private static final HandlerExecutorServiceImpl mExecutorService;

    static {
        sBitmapLoadThread = new HandlerThread("Bitmap-Load Thread");
        sBitmapLoadThread.start();
        mExecutorService = new HandlerExecutorServiceImpl(new Handler(sBitmapLoadThread.getLooper()));
    }

    public static void fetchRecyclerViewImage(SimpleDraweeView view, Uri uri, boolean withProgress) {
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
            ImageProgressBarDrawable drawable = null;
            if (withProgress) {
                drawable = new ImageProgressBarDrawable(BeanApp.getApp().getResources());
            }
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

    public static void fetchRecyclerViewImage(SimpleDraweeView view, String uri) {
        int res = 0;
        try {
            res = Integer.parseInt(uri);
        } catch (Exception e) {
        }
        Uri u = null;
        if (res > 0) {
            u = buildResUri(res);
        } else {
            u = buildStringUri(uri);
        }
        fetchRecyclerViewImage(view, u, true);
    }

    public static void fetchRecyclerViewImage(SimpleDraweeView view, Uri uri) {
        fetchRecyclerViewImage(view, uri, true);
    }

    public static void fetchPictureIntoImageView(final ImageView view, Uri uri) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                                                  .setRequestPriority(Priority.HIGH)
                                                  .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> source = imagePipeline.fetchDecodedImage(request, null);
        source.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, mExecutorService);
    }

    public static void closeImageViewBitmap(final ImageView view) {
        if (view == null) {
            return;
        }
        view.setImageBitmap(null);
    }

    public static Uri buildResUri(int resId) {
        Uri uri = (new Uri.Builder()).scheme("res").path(String.valueOf(resId)).build();
        return uri;
    }

    public static Uri buildStringUri(String url) {
        return Uri.parse(url);
    }
}
