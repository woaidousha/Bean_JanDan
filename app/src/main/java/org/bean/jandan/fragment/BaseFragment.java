package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseFragment extends Fragment {

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
}
