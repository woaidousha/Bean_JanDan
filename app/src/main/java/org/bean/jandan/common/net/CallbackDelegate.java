package org.bean.jandan.common.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.common.util.JsonUtil;

import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class CallbackDelegate<T> implements Callback {

    private Class<T> mResultClass;
    private OnResultCallback callback;

    public CallbackDelegate(OnResultCallback callback, Class<T> resultClass) {
        this.callback = callback;
        this.mResultClass = resultClass;
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        callback.onFinish();
        callback.onFailure(request, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        String result = response.body().string();
        final T t = JsonUtil.gson().fromJson(result, mResultClass);
        callback.onResponse(response, t);
        callback.onFinish();
    }
}
