package org.bean.jandan.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.okhttp.Response;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.common.page.Page;
import org.bean.jandan.common.page.PageHelper;
import org.bean.jandan.model.Result;
import org.bean.jandan.widget.AutoLoadSwipeRefreshLayout;
import org.bean.jandan.widget.LoadListener;

import java.util.List;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public abstract class BaseRecycleViewNetFragment<T extends Result> extends
        BaseNetFragment<T> implements LoadListener {

    protected CommonRecycleAdapter mAdapter;

    protected Page mPage;
    protected AutoLoadSwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecycleView;

    protected abstract String url();
    protected abstract CommonRecycleAdapter configAdapter();

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

    @Override
    protected void onInit() {
        mPage = new Page(url());
        mAdapter = configAdapter();
        mRecycleView.setAdapter(mAdapter);
        load(true);
    }

    @Override
    public boolean load(boolean head) {
        if (getActivity() == null || isDetached()) {
            return false;
        }
        mSwipeRefreshLayout.setRefreshing(true);
        return request(PageHelper.createPageReq(mPage, head), this);
    }

    public void goToTop() {
        int position = mRecycleView.getChildPosition(mRecycleView.getChildAt(0));
        if (position > 10) {
            mRecycleView.scrollToPosition(0);
        } else {
            mRecycleView.smoothScrollToPosition(0);
        }
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
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailed() {
    }

    @Override
    public void onFinish() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
