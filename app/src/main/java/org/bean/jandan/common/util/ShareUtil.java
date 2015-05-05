package org.bean.jandan.common.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.bean.jandan.BeanApp;
import org.bean.jandan.R;
import org.bean.jandan.common.C;

import java.io.File;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */
public class ShareUtil {

    public static void shareImage(final Uri uri) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                                                  .setRequestPriority(Priority.HIGH)
                                                  .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, null);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                String path = StorageUtil.getWritePath(StringUtil.makeMd5(uri.toString()), C.Directory.SHARE);
                AttachmentStore.saveBitmap(bitmap, path, false);
                Intent intent = new Intent(Intent.ACTION_SEND);
                File file = new File(path);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                intent.setType("image/jpeg");
                String title = BeanApp.getApp().getString(R.string.button_text_share);
                BeanApp.getApp().startActivity(Intent.createChooser(intent,title).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, UiThreadImmediateExecutorService.getInstance());

    }

}
