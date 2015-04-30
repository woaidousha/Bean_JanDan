package org.bean.jandan;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class BeanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
