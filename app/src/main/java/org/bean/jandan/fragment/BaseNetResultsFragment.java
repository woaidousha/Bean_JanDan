package org.bean.jandan.fragment;

import android.os.Bundle;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.cache.CacheManager;
import org.bean.jandan.common.cache.Cacheable;
import org.bean.jandan.common.cache.DataObserver;
import org.bean.jandan.common.net.CallbackDelegate;
import org.bean.jandan.common.net.HttpUtil;
import org.bean.jandan.common.net.OnResultCallback;
import org.bean.jandan.common.util.DebugLog;
import org.bean.jandan.common.util.JsonUtil;
import org.bean.jandan.model.Result;
import org.bean.jandan.widget.LoadListener;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/14.
 */
public abstract class BaseNetResultsFragment<T extends Result> extends BaseFragment implements
        OnResultCallback<T>, LoadListener, DataObserver<String> {

    private Class<T> mResultClass;

    protected abstract String url();
    protected abstract AdapterDataSource getDataSource();

    public BaseNetResultsFragment() {
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
    }

    @Override
    protected void onInit() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheManager.get().cache().registerDataObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        CacheManager.get().cache().unregisterDataObserver(this);
    }

    @Override
    public boolean load(boolean head) {
        if (getActivity() == null || isDetached()) {
            return false;
        }
        preload();
        Request request = buildRequest(head);
        return request(request, mResultClass);
    }

    protected boolean request(Request request, Class<T> clazz) {
        if (request == null) {
            return false;
        }
        HttpUtil.enqueue(request, new CallbackDelegate<T>(this, clazz));
        return true;
    }

    @Override
    public void onFailure(Request request, IOException e) {
    }

    protected Request buildRequest(boolean head) {
        return HttpUtil.buildReq(url(), null);
    }

    @Override
    public void onResponse(final Response response, final T t) {
        if (getActivity() == null || t == null || t.getResults() == null) {
            return;
        }

        Object tag = response.request().tag();
        Boolean isFirstPage = true;
        if (tag != null && tag instanceof Boolean) {
            isFirstPage = (Boolean) response.request().tag();
        }
        List<Cacheable> res = t.getResults();
        if (isFirstPage) {
            CacheManager.get().cache().appendTo(url(), res);
        } else {
            CacheManager.get().cache().append(url(), res);
        }
    }

    @Override
    public T parseResult(Response response, Class<T> classOfT) throws IOException {
        T result = null;
        result = JsonUtil.gson().fromJson(response.body().charStream(), mResultClass);
        return result;
    }

    @Override
    public void onDataChange(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DebugLog.d("s : " + s);
                getDataSource().notifyDataChanged();
            }
        });
    }

    @Override
    public String getKey() {
        return url();
    }
}
