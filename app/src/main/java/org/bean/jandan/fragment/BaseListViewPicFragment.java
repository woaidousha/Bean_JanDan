package org.bean.jandan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.R;
import org.bean.jandan.common.adapter.AdapterDelegate;
import org.bean.jandan.common.adapter.CommonAdapter;
import org.bean.jandan.common.adapter.ViewHolder;
import org.bean.jandan.adapter.vh.PictureViewHolder;
import org.bean.jandan.model.Page;
import org.bean.jandan.model.PictureResult;
import org.bean.jandan.model.Result;
import org.bean.jandan.model.SinglePicture;
import org.bean.jandan.widget.LoadListener;
import org.bean.jandan.widget.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseListViewPicFragment extends BaseNetFragment<PictureResult> implements LoadListener {

    private Page mPage;
    private ArrayList<SinglePicture> mPictures = new ArrayList<>();
    private CommonAdapter mAdapter;

    protected PullToRefreshListView mListView;

    protected abstract String url();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initList();
    }

    @Override
    protected int viewId() {
        return R.layout.fragment_list_view_picture_layout;
    }

    protected void findViews(View v) {
        mListView = (PullToRefreshListView) v.findViewById(R.id.list_view);
    }

    private void initList() {
        mPage = new Page();
        mListView.setLoadListener(this);
        mAdapter = new CommonAdapter(this, mPictures, new AdapterDelegate() {
            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public Class<? extends ViewHolder> viewHolderAtPosition(int position) {
                return PictureViewHolder.class;
            }

            @Override
            public boolean enabled(int position) {
                return true;
            }
        });
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean load(boolean head) {
        String url = null;
        if (!head) {
            url = mPage.nextPage(url());
        } else {
            url = mPage.firstPage(url());
        }
        return request(buildRequest(url), this);
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
        for (SinglePicture singlePicture : pictureResult.getResults()) {
            mPictures.remove(singlePicture);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result instanceof PictureResult) {
                    Boolean isFirstPage  = (Boolean) response.request().tag();
                    if (isFirstPage) {
                        mPictures.addAll(0, pictureResult.getResults());
                    } else {
                        mPictures.addAll(pictureResult.getResults());
                    }
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
}
