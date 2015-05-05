package org.bean.jandan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.CommenRecycleAdapter;
import org.bean.jandan.common.adapter.viewHolder.PictureRViewHolder;
import org.bean.jandan.model.Page;
import org.bean.jandan.model.PictureResult;
import org.bean.jandan.model.Result;
import org.bean.jandan.model.SinglePicture;
import org.bean.jandan.widget.AutoLoadSwipeRefreshLayout;
import org.bean.jandan.widget.LoadListener;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class BaseRecycleViewPicFragment extends BaseNetFragment<PictureResult> implements LoadListener {

    private Page mPage;
    private ArrayList<SinglePicture> mPictures = new ArrayList<>();
    private PictureAdapter mAdapter;

    protected AutoLoadSwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecycleView;

    protected abstract String url();

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
        mAdapter = new PictureAdapter();
        mRecycleView.setAdapter(mAdapter);
        load(true);
    }

    @Override
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
        return builder.url(url).tag(mPage.isFirstPage(url)).build();
    }

    @Override
    public void onSuccess(final Result result, final Response response) {
        final PictureResult pictureResult = ((PictureResult) result);
        for (SinglePicture singlePicture : pictureResult.getPictures()) {
            mPictures.remove(singlePicture);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result instanceof PictureResult) {
                    Boolean isFirstPage = (Boolean) response.request().tag();
                    if (isFirstPage) {
                        mPictures.addAll(0, pictureResult.getPictures());
                    } else {
                        mPictures.addAll(pictureResult.getPictures());
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onFailed() {
    }

    @Override
    public void onFinish() {
    }

    @Override
    protected Class getResultClazz() {
        return PictureResult.class;
    }

    class PictureAdapter extends CommenRecycleAdapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new PictureRViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_picture_item,
                    viewGroup, false));
        }

        @Override
        public Object getItem(int position) {
            return mPictures.get(position);
        }

        @Override
        public int getItemCount() {
            return mPictures == null ? 0 : mPictures.size();
        }
    }
}
