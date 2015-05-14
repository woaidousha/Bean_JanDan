package org.bean.jandan.fragment;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.net.HttpUtil;
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
public abstract class BaseNetResultsFragment<T extends Result> extends BaseNetFragment<T> implements LoadListener {

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
    public boolean load(boolean head) {
        if (getActivity() == null || isDetached()) {
            return false;
        }
        preload();
        Request request = buildRequest(head);
        return request(request, mResultClass);
    }

    protected Request buildRequest(boolean head) {
        Request request = HttpUtil.buildReq(url(), null);;
        return request;
    }

    @Override
    public void onResponse(final Response response, final T t) {
        if (getActivity() == null || t == null || t.getResults() == null) {
            return;
        }
        for (Object object : t.getResults()) {
            getDataSource().remove(object);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Object tag = response.request().tag();
                Boolean isFirstPage = true;
                if (tag != null && tag instanceof Boolean) {
                    isFirstPage = (Boolean) response.request().tag();
                }
                List res = t.getResults();
                if (isFirstPage) {
                    getDataSource().addAll(0, res);
                } else {
                    getDataSource().addAll(res);
                }
                getDataSource().notifyDataSetChanged();
            }
        });
    }

    @Override
    public T parseResult(Response response, Class<T> classOfT) {
        T result = null;
        try {
            result = JsonUtil.gson().fromJson(response.body().string(), mResultClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
