package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.common.net.CallbackDelegate;
import org.bean.jandan.common.net.HttpUtil;
import org.bean.jandan.common.net.OnResultCallback;
import org.bean.jandan.common.util.JsonUtil;
import org.bean.jandan.model.Result;

import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseNetFragment<T extends Result> extends Fragment implements Callback, OnResultCallback {

    protected abstract Request buildRequest(String url);

    protected abstract int viewId();
    protected abstract Class getResultClazz();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(viewId(), null);
        return view;
    }

    protected boolean request(Request request, final OnResultCallback callback) {
        if (request == null) {
            return false;
        }
        HttpUtil.enqueue(request, new CallbackDelegate(this) {
            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
        return true;
    }

    @Override
    public void onFailure(Request request, IOException e) {
    }

    @Override
    public void onResponse(Response response) throws IOException {
        String result = response.body().string();
        final T t = (T) JsonUtil.gson().fromJson(result, getResultClazz());
        onSuccess(t, response);
    }

}
