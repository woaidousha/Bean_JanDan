package org.bean.jandan.common.util;

import android.content.Intent;
import android.net.Uri;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.bean.jandan.BeanApp;
import org.bean.jandan.R;
import org.bean.jandan.common.C;
import org.bean.jandan.model.SinglePicture;

import java.io.File;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */
public class ShareUtil {

    public static void shareImage(final SinglePicture picture) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(picture.getPicUri())
                                                  .setRequestPriority(Priority.HIGH)
                                                  .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<PooledByteBuffer>> source = imagePipeline.fetchEncodedImage(request, null);
        source.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                if (dataSource.isFinished()) {
                    CloseableReference<PooledByteBuffer> reference = dataSource.getResult();
                    try {
                        PooledByteBuffer buffer = reference.get();
                        String path = StroageUtil.getWritePath(StringUtil.makeMd5(picture.getPic()), C.Directory
                                .SHARE);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        if (picture.isGif()) {
                            intent.setType("image/gif");
                        } else {
                            intent.setType("image/jpeg");
                        }
                        AttachmentStore.savePooledByteBuffer(buffer, path);
                        File file = new File(path);
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        String title = BeanApp.getApp()
                                              .getString(R.string.button_text_share);
                        BeanApp.getApp()
                               .startActivity(Intent.createChooser(intent, title)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } finally {
                        CloseableReference.closeSafely(reference);
                    }
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {

            }
        }, UiThreadImmediateExecutorService.getInstance());
    }

}
