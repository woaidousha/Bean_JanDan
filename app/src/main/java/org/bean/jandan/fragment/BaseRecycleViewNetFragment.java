package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.CommenRecycleAdapter;
import org.bean.jandan.model.Page;
import org.bean.jandan.model.Result;
import org.bean.jandan.widget.AutoLoadSwipeRefreshLayout;
import org.bean.jandan.widget.LoadListener;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public abstract class BaseRecycleViewNetFragment<T extends Result, E extends CommenRecycleAdapter> extends
        BaseNetFragment<T> implements LoadListener {

    protected E mAdapter;

    protected Page mPage;
    protected AutoLoadSwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecycleView;

    protected abstract String url();
    protected abstract E configAdapter();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initList();
    }

    @Override
    protected int viewId() {
        return R.layout.fragment_recycle_view_picture_layout;
    }

    protected void findViews(View v) {
        mSwipeRefreshLayout = (AutoLoadSwipeRefreshLayout) v.findViewById(R.id.swipe_layout);
        mRecycleView = (RecyclerView) v.findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setRecyclerView(mRecycleView, null);
        mSwipeRefreshLayout.setLoadListener(this);
    }

    private void initList() {
        mPage = new Page();
        mAdapter = configAdapter();
        mRecycleView.setAdapter(mAdapter);
        load(true);
    }

    public boolean load(boolean head) {
        String url = null;
        if (!head) {
            url = mPage.nextPage(url());
        } else {
            url = mPage.firstPage(url());
        }
        mSwipeRefreshLayout.setRefreshing(true);
        return request(buildRequest(url), this);
    }

    public void goToTop() {
        mRecycleView.smoothScrollToPosition(0);
    }

    @Override
    protected Request buildRequest(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request.Builder builder = new Request.Builder();
        return builder.url(url)
                      .tag(mPage.isFirstPage(url))
                      .build();
    }

    @Override
    public void onSuccess(final Result result, final Response response) {
        for (Object object : result.getResults()) {
            mAdapter.remove(object);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Boolean isFirstPage = (Boolean) response.request().tag();
                List res = result.getResults();
                if (isFirstPage) {
                    mAdapter.addAll(0, res);
                } else {
                    mAdapter.addAll(res);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailed() {
    }

    @Override
    public void onFinish() {
    }
}
