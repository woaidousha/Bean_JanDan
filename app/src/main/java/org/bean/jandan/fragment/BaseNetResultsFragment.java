package org.bean.jandan.fragment;

import com.squareup.okhttp.Response;

import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.page.Page;
import org.bean.jandan.common.page.PageHelper;
import org.bean.jandan.model.Result;
import org.bean.jandan.widget.LoadListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/14.
 */
public abstract class BaseNetResultsFragment<T extends Result> extends BaseNetFragment<T> implements LoadListener {

    private Class<T> mResultClass;

    protected Page mPage;

    protected abstract String url();
    protected abstract <E> AdapterDataSource<E> getDataSource();

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
        mPage = new Page(url());
    }

    @Override
    public boolean load(boolean head) {
        if (getActivity() == null || isDetached()) {
            return false;
        }
        preload();
        return request(PageHelper.createPageReq(mPage, head), mResultClass);
    }

    @Override
    public void onResponse(final Response response, final T t) {
        if (getActivity() == null) {
            return;
        }
        for (Object object : t.getResults()) {
            getDataSource().remove(object);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Boolean isFirstPage = (Boolean) response.request().tag();
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
}
