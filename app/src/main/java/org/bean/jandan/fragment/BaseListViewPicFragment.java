package org.bean.jandan.fragment;

import android.os.Bundle;
import android.view.View;

import com.squareup.okhttp.Response;

import org.bean.jandan.R;
import org.bean.jandan.adapter.vh.PictureViewHolder;
import org.bean.jandan.common.adapter.AdapterDelegate;
import org.bean.jandan.common.adapter.CommonAdapter;
import org.bean.jandan.common.adapter.ViewHolder;
import org.bean.jandan.common.page.Page;
import org.bean.jandan.common.page.PageHelper;
import org.bean.jandan.model.PictureResult;
import org.bean.jandan.model.Result;
import org.bean.jandan.model.SinglePicture;
import org.bean.jandan.widget.LoadListener;
import org.bean.jandan.widget.PullToRefreshListView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class BaseListViewPicFragment<T extends Result> extends BaseNetResultsFragment<T> implements
        LoadListener {

    private Class<T> mResultClass;

    private Page mPage;
    private ArrayList<SinglePicture> mPictures = new ArrayList<>();
    private CommonAdapter mAdapter;

    protected PullToRefreshListView mListView;

    protected abstract String url();

    public BaseListViewPicFragment() {
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
        mPage = new Page(url());
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
        return request(PageHelper.createPageReq(mPage, head), mResultClass);
    }

    @Override
    public void onResponse(final Response response, final T result) {
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
    public void onFinish() {
    }

}
