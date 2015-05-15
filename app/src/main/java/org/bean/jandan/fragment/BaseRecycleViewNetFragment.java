package org.bean.jandan.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.okhttp.Request;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.AdapterDataSource;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.common.page.Page;
import org.bean.jandan.common.page.PageHelper;
import org.bean.jandan.model.Result;
import org.bean.jandan.widget.AutoLoadSwipeRefreshLayout;
import org.bean.jandan.widget.LoadListener;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public abstract class BaseRecycleViewNetFragment<T extends Result> extends BaseNetResultsFragment<T> implements
        LoadListener {

    protected Page mPage;
    protected CommonRecycleAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    protected AutoLoadSwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecycleView;

    protected abstract CommonRecycleAdapter configAdapter();

    @Override
    protected int viewId() {
        return R.layout.fragment_recycle_view_picture_layout;
    }

    @Override
    protected void findViews(View v) {
        mSwipeRefreshLayout = (AutoLoadSwipeRefreshLayout) v.findViewById(R.id.swipe_layout);
        mRecycleView = (RecyclerView) v.findViewById(R.id.recycle_view);
    }

    @Override
    protected void onInit() {
        super.onInit();
        mPage = new Page(url());
        mAdapter = configAdapter();
        mRecycleView.setAdapter(mAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setTag(url());
        mSwipeRefreshLayout.setRecyclerView(mRecycleView, null);
        mSwipeRefreshLayout.setLoadListener(this);
        load(true);
    }

    @Override
    public void preload() {
        mSwipeRefreshLayout.setRefresh(true, true);
    }

    @Override
    public boolean hasMore() {
        return !mPage.isLastPage();
    }

    @Override
    protected Request buildRequest(boolean head) {
        return PageHelper.createPageReq(mPage, head);
    }

    public void goToTop() {
        mSwipeRefreshLayout.goToTop();
    }

    @Override
    public void onFinish() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected AdapterDataSource getDataSource() {
        return mAdapter;
    }
}
