package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseNetFragment<T extends Result> extends Fragment implements Callback, OnResultCallback {

    private static final String TAG = "BaseNetFragment";
    private Class<T> mResultClass;

    protected abstract Request buildRequest(String url);

    protected abstract int viewId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(viewId(), null);
        Type genericSuperclass = getClass().getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            mResultClass =(Class<T>) parameterizedType.getActualTypeArguments()[0];
            if (mResultClass == null) {
                throw new RuntimeException(getClass().getName() + " 出错了 查查啥问题吧");
            }
        } else {
            throw new RuntimeException(getClass().getName() + " 你在父类中实现了泛型，或者你没有实现泛型。");
        }
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
        final T t = JsonUtil.gson().fromJson(result, mResultClass);
        onSuccess(t, response);
    }

}
