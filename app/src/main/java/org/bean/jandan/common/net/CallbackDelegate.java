package org.bean.jandan.common.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public abstract class CallbackDelegate implements Callback {

    private Callback callback;

    public CallbackDelegate(Callback callback) {
        this.callback = callback;
    }

    public abstract void onFinish();

    @Override
    public void onFailure(Request request, IOException e) {
        onFinish();
        callback.onFailure(request, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        callback.onResponse(response);
        onFinish();
    }
}
