package org.bean.jandan.common.net;

import com.squareup.okhttp.Response;

import org.bean.jandan.model.Result;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public interface OnResultCallback {
    public void onFinish();

    public void onFailed();

    /** run in ui thread */
    public void onSuccess(Result result, Response response);
}
