package org.bean.jandan.common.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class HttpUtil {

    private static OkHttpClient sClient = null;

    public static OkHttpClient client() {
        if (sClient == null) {
            sClient = new OkHttpClient();
            sClient.setRetryOnConnectionFailure(true);
        }
        return sClient;
    }

    public static void enqueue(Request request, Callback callback) {
        client().newCall(request)
                .enqueue(callback);
    }

    public static Request buildReq(String url, Object tag) {
        Request.Builder builder = new Request.Builder();
        return builder.url(url).tag(tag).build();
    }

    public static Request buildReq(String url) {
        return buildReq(null);
    }
}
