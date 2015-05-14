package org.bean.jandan.common.net;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public interface OnResultCallback<T> {
    public void onFinish();

    public void onResponse(Response response, T t);

    public void onFailure(Request request, IOException e);
}
