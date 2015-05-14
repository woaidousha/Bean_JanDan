package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.Request;

import org.bean.jandan.BeanApp;
import org.bean.jandan.common.net.CallbackDelegate;
import org.bean.jandan.common.net.HttpUtil;
import org.bean.jandan.common.net.OnResultCallback;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseNetFragment<T> extends Fragment implements OnResultCallback<T> {

    private static final String TAG = "BaseNetFragment";
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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    protected boolean request(Request request, Class clazz) {
        if (request == null) {
            return false;
        }
        HttpUtil.enqueue(request, new CallbackDelegate(this, clazz));
        return true;
    }

    @Override
    public void onFailure(Request request, IOException e) {
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BeanApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
