package org.bean.jandan.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import static org.bean.jandan.widget.pulltorefresh.State.PULL_TO_REFRESH;
import static org.bean.jandan.widget.pulltorefresh.State.RELEASE_TO_REFRESH;
import static org.bean.jandan.widget.pulltorefresh.State.RESET;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh {

    private static final int FRICTION = 2;

    private int mTouchSlop;
    private boolean mIsBeingDragged;

    private T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;

    private LoadingLayout mHeaderLayout;
    private LoadingLayout mFooterLayout;

    private Mode mCurrentMode = Mode.BOTH;
    private State mState = RESET;
    private float mLastMotionX;
    private float mInitialMotionX;
    private float mLastMotionY;
    private float mInitialMotionY;

    private Scroller mScroller;

    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mScroller = new Scroller(context);

        //创建，添加需要刷新的view
        mRefreshableView = createRefreshabkeView(context);
        addRefreshableView(context, mRefreshableView);

        //创建，添加头位显示的加载中的view
        mHeaderLayout = createLoadingLayout(context,attrs);
        mFooterLayout = createLoadingLayout(context,attrs);
    }

    private void addRefreshableView(Context context, T refreshableView) {
        mRefreshableViewWrapper = new FrameLayout(context);
        mRefreshableViewWrapper.addView(refreshableView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout
                .LayoutParams.MATCH_PARENT);
        addViewInternal(mRefreshableViewWrapper, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        T refreshableView = getRefreshableView();

        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(child, index, params);
        } else {
            throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
        }
    }

    protected final void addViewInternal(View child, ViewGroup.LayoutParams params) {
        super.addView(child, -1, params);
    }

    protected abstract T createRefreshabkeView(Context context);

    private LoadingLayout createLoadingLayout(Context context, AttributeSet attrs) {
        LoadingLayout layout = new LoadingLayout(context, attrs);
        return layout;
    }

    private void updateUIforMode() {

        LinearLayout.LayoutParams lp = getLoadingLayoutParams();

        if (mHeaderLayout.getParent() == this) {
            removeView(mHeaderLayout);
        }
        addView(mHeaderLayout, 0, lp);

        if (mFooterLayout.getParent() == this) {
            removeView(mFooterLayout);
        }
        addView(mFooterLayout, lp);

        refreshLoadingViewsSize();
    }

    private LinearLayout.LayoutParams getLoadingLayoutParams() {
        return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private void refreshLoadingViewsSize() {
        int maxPullScroll = (int) (getMaxPullScroll() * 1.2f);

        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();

        mHeaderLayout.setHeight(maxPullScroll);
        pTop = -maxPullScroll;

        mFooterLayout.setHeight(maxPullScroll);
        pBottom = -maxPullScroll;

        setPadding(pLeft, pTop, pRight, pBottom);
    }

    @Override
    public T getRefreshableView() {
        return mRefreshableView;
    }

    public int getMaxPullScroll() {
        return Math.round(getHeight() / FRICTION);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        //已经在拉动并且是按下事件，就拦截
        if (action == MotionEvent.ACTION_DOWN && mIsBeingDragged) {
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isReadyForPull()) {
                    //如果已经满足刷新条件，就判定为开始拖拽，并拦截事件
                    mLastMotionX = mInitialMotionX = ev.getX();
                    mLastMotionY = mInitialMotionY = ev.getY();
                    mIsBeingDragged = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (isReadyForPull()) {
                    float x = ev.getX();
                    float y = ev.getY();

                    float diff = Math.abs(mLastMotionX - x);
                    float oppositeDiff = Math.abs(mLastMotionY - y);

                    if (diff > mTouchSlop && diff  > oppositeDiff) {
                        mLastMotionX = x;
                        mLastMotionY = y;
                        if (isReadyForPullStart()) {
                            mIsBeingDragged = true;
                            mCurrentMode = Mode.PULL_FROM_START;
                        } else if (isReadyForPullEnd()) {
                            mIsBeingDragged = true;
                            mCurrentMode = Mode.PULL_FROM_END;
                        }
                    }
                }
                break;
        }

        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isReadyForPull()) {
                    mLastMotionY = mInitialMotionY = event.getY();
                    mLastMotionX = mInitialMotionX = event.getX();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsBeingDragged) {
                    mLastMotionX = event.getX();
                    mLastMotionY = event.getY();
                    pullEvent();
                    return true;
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 计算当前滑动距离与头尾view的大小关系来确定当前状态
     * */
    private void pullEvent() {
        float moveDiff = mInitialMotionY - mLastMotionY;
        int itemDimension = 0;
        int newScrollValue = 0;

        switch (mCurrentMode) {
            case PULL_FROM_START:
                newScrollValue = Math.round(Math.min(moveDiff, 0) / FRICTION);
                itemDimension = getHeaderSize();
                break;
            case PULL_FROM_END:
                newScrollValue = Math.round(Math.max(moveDiff, 0) / FRICTION);
                itemDimension = getFooterSize();
                break;
        }

        //设置滑动位置
        setHeaderScroll(newScrollValue);

        //设置当前状态
        newScrollValue = Math.abs(newScrollValue);
        if (newScrollValue > 0 && !isRefreshing()) {
            if (itemDimension > newScrollValue) {
                setState(PULL_TO_REFRESH);
            } else if (itemDimension < newScrollValue && mState == PULL_TO_REFRESH) {
                setState(RELEASE_TO_REFRESH);
            }
        }
    }

    private void setHeaderScroll(int newScrollValue) {
        scrollTo(0, newScrollValue);
    }

    private void setState(State state) {
        mState = state;

        switch (mState) {
            case RESET:
                onReset();
                break;
            case PULL_TO_REFRESH:
                onPullToRefresh();
                break;
            case RELEASE_TO_REFRESH:
                onReleaseRefresh();
                break;
            case REFRESHING:
            case MANUAL_REFRESHING:
                onRefreshing();
                break;
        }

    }

    private void onRefreshing() {

    }

    private void onReleaseRefresh() {

    }

    private void onPullToRefresh() {

    }

    private void onReset() {
        mIsBeingDragged = false;
        // Always reset both layouts, just in case...
        mHeaderLayout.reset();
        mFooterLayout.reset();

        smoothScrollTo(0);
    }

    private void smoothScrollTo(int y) {
        mScroller.startScroll(0, getScrollY(), 0, 0);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
        }
    }

    public boolean isReadyForPull() {
        return isReadyForPullStart() || isReadyForPullEnd();
    }

    protected abstract boolean isReadyForPullEnd();

    protected abstract boolean isReadyForPullStart();

    public int getHeaderSize() {
        return mHeaderLayout.getHeight();
    }

    public int getFooterSize() {
        return mFooterLayout.getHeight();
    }
}
