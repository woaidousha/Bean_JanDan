package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.BeanApp;
import org.bean.jandan.common.net.CallbackDelegate;
import org.bean.jandan.common.net.HttpUtil;
import org.bean.jandan.common.net.OnResultCallback;
import org.bean.jandan.common.util.JsonUtil;
import org.bean.jandan.model.Result;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseNetFragment<T extends Result> extends Fragment implements Callback, OnResultCallback {

    private static final String TAG = "BaseNetFragment";
    private Class<T> mResultClass;
    private AtomicBoolean mHasInited = new AtomicBoolean(false);

    protected abstract int viewId();
    protected abstract void findViews(View v);
    protected abstract void onInit();

    protected void init() {
        if (mHasInited.compareAndSet(false, true)) {
            onInit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(viewId(), null);
        Type genericSuperclass = getClass().getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            mResultClass =(Class<T>) parameterizedType.getActualTypeArguments()[0];
            if (mResultClass == null) {
                throw new RuntimeException(getClass().getName() + " error");
            }
        } else {
            throw new RuntimeException(getClass().getName() + ", it will not run to here");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
    }

    protected boolean request(Request request, final OnResultCallback callback) {
        if (request == null) {
            return false;
        }
        HttpUtil.enqueue(request, new CallbackDelegate(this) {
            @Override
            public void onFinish() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinish();
                    }
                });
            }
        });
        return true;
    }

    @Override
    public void onFailure(Request request, IOException e) {
    }

    @Override
    public void onResponse(final Response response) throws IOException {
        String result = response.body().string();
        final T t = JsonUtil.gson().fromJson(result, mResultClass);
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(t, response);
            }
        });
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BeanApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
