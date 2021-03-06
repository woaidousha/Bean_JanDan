package org.bean.jandan;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLogDefaultLoggingDelegate;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import org.bean.jandan.common.C;
import org.bean.jandan.common.crash.AppCrashHandler;
import org.bean.jandan.common.util.StroageUtil;

import java.io.File;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class BeanApp extends Application {

    private static BeanApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppCrashHandler.getInstance(this);
        initFresco();
        StroageUtil.init();
    }

    public static BeanApp getApp() {
        return sInstance;
    }

    public void initFresco() {
        FLogDefaultLoggingDelegate.getInstance().setMinimumLoggingLevel(0);
        ProgressiveJpegConfig jpegConfig = new SimpleProgressiveJpegConfig();
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(this);
        DiskCacheConfig.Builder diskCacheConfig = DiskCacheConfig.newBuilder();
        diskCacheConfig.setBaseDirectoryPathSupplier(new
            Supplier<File>() {
                @Override
                public File get() {
                    return BeanApp.this.getExternalCacheDir();
                }
            })
        .setBaseDirectoryName(C.DISK_CACHE.CACHE_PATH)
        .setMaxCacheSize(C.DISK_CACHE.MAX_CACHE_SIZE)
        .setMaxCacheSizeOnLowDiskSpace(C.DISK_CACHE.MAX_CACHE_SIZE_ON_LOW_DISK_SPACE)
        .setMaxCacheSizeOnVeryLowDiskSpace(C.DISK_CACHE.MAX_CACHE_SIZE_ON_VERY_LOW_DISK_SPACE);
        configBuilder.setMainDiskCacheConfig(diskCacheConfig.build());
        configBuilder.setProgressiveJpegConfig(jpegConfig);

        Fresco.initialize(this, configBuilder.build());
    }
}
