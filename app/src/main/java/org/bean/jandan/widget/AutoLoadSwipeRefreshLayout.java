package org.bean.jandan.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public class AutoLoadSwipeRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (mDelegateListener != null) {
                mDelegateListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mDelegateListener != null) {
                mDelegateListener.onScrolled(recyclerView, dx, dy);
            }
            if (isRefreshing()) {
                return;
            }
            if (mLoadListener != null && !mLoadListener.hasMore()) {
                return;
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            View view = layoutManager.findViewByPosition(layoutManager.getItemCount() - 1);
            if (view == null) {
                return;
            }
            int lastVisibleItem = layoutManager.getPosition(view) + recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                setRefreshing(true);
                mLoadMore = true;
                onRefresh();
            }
        }
    };

    private RecyclerView.OnScrollListener mDelegateListener;
    private OnRefreshListener mDelegateRefreshListener;
    private LoadListener mLoadListener;

    private boolean mLoadMore;

    public AutoLoadSwipeRefreshLayout(Context context) {
        super(context);
        super.setOnRefreshListener(this);
    }

    public AutoLoadSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnRefreshListener(this);
    }

    public void setRecyclerView(RecyclerView recyclerView, RecyclerView.OnScrollListener delegateListener) {
        mRecyclerView = recyclerView;
        mDelegateListener = delegateListener;
        mRecyclerView.setOnScrollListener(mOnScrollListener);
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        mDelegateRefreshListener = listener;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (!refreshing && mLoadMore) {
            mLoadMore = false;
        }
        super.setRefreshing(refreshing);
    }

    public void setRefresh(boolean refreshing, boolean notify) {
        if (!notify) {
            this.setRefreshing(refreshing);
        } else {
            try {
                Method method = getClass().getSuperclass().getDeclaredMethod("setRefreshing", new Class[]{boolean.class, boolean.class});
                method.setAccessible(true);
                method.invoke(this, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mLoadListener != null) {
            if (mLoadMore) {
                mLoadListener.load(false);
            } else {
                mLoadListener.load(true);
            }
        }
        if (mDelegateRefreshListener != null) {
            mDelegateRefreshListener.onRefresh();
        }
    }

    public void goToTop() {
        int position = mRecyclerView.getChildPosition(mRecyclerView.getChildAt(0));
        if (position > 10) {
            mRecyclerView.scrollToPosition(0);
        } else {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
