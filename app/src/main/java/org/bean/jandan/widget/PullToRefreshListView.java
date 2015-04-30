package org.bean.jandan.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.bean.jandan.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

    public interface StateListener {
        public void updateLoadState(State state);
    }

    public enum State {
        //正常状态
        RESET,
        //尾部自动加载中
        END_LOADING,
        //下拉中
        PULL_TO_REFRESH,
        //下拉至可刷新状态
        RELEASE_TO_REFRESH,
        //头部刷新中
        HEAD_LOADING
    }

    public enum Position {
        HEAD,
        END
    }

    private static final int ANIMATION_DURATION = 200;
    static final float FRICTION = 2.0f;

    private DataSetObserver mLoadStateObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            synchronized (mState) {
                updateState(State.RESET);
            }
        }
    };

    private LoadListener mLoadListener;

    private StateListener mStateListener;

    private State mState = State.RESET;

    private OnScrollListener mScrollListener;

    private int mTouchSlop;
    private boolean mIsBeingDragged;
    private float mLastMotionX;
    private float mInitialMotionX;
    private float mLastMotionY;
    private float mInitialMotionY;
    private boolean mPullDown;

    private HashMap<Position, IndicatorDrawer> mIndicatorDrawers = new HashMap<>();

    //头部加载中指示器最大高度
    private int mHeadIndicatorMaxHeight;
    private int mHeadIndicatorHeight = Integer.MAX_VALUE;
    private HeadIndicatorDrawer mHeadIndicatorDrawer;

    //底部加载中指示器最小高度
    private int mEndIndicatorMinHeight;
    private int mEndIndicatorHeight = Integer.MAX_VALUE;
    private EndIndicatorDrawer mEndIndicatorDrawer;

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setStateListener(StateListener listener) {
        this.mStateListener = listener;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
    }

    private void updateState(State state) {
        State cur = mState;
        mState = state;
        if (mState == State.RESET) {
            mPullDown = false;
        }
        startIndicatorAnimation(cur, state);
        if (mState == State.HEAD_LOADING) {
            if (mLoadListener != null) {
                mLoadListener.load(true);
            }
        }
        if (mStateListener != null) {
            mStateListener.updateLoadState(mState);
        }
    }

    private void startIndicatorAnimation(State cur, State after) {
        switch (after) {
            case RESET: {
                switch (cur) {
                    case END_LOADING:
                        startEndIndicatorAnimation();
                        break;
                    case PULL_TO_REFRESH:
                    case RELEASE_TO_REFRESH:
                    case HEAD_LOADING:
                        startHeadIndicatorAnimation(true);
                        break;
                }
                break;
            }
            case END_LOADING: {
                startEndIndicatorAnimation();
                break;
            }
            case HEAD_LOADING: {
                startHeadIndicatorAnimation(true);
                break;
            }
            case PULL_TO_REFRESH: {
                startHeadIndicatorAnimation(false);
                break;
            }
        }
    }

    private void startHeadIndicatorAnimation(boolean post) {
        if (mHeadIndicatorDrawer == null) {
            mHeadIndicatorDrawer = new HeadIndicatorDrawer();
            mIndicatorDrawers.put(Position.HEAD, mHeadIndicatorDrawer);
        }
        if (post) {
            mHeadIndicatorDrawer.reset();
            ViewCompat.postOnAnimation(this, mHeadIndicatorDrawer);
        } else {
            mHeadIndicatorDrawer.invalidate();
        }
    }

    private void startEndIndicatorAnimation() {
        if (mEndIndicatorDrawer == null) {
            mEndIndicatorDrawer = new EndIndicatorDrawer();
            mIndicatorDrawers.put(Position.END, mEndIndicatorDrawer);
        } else {
            mEndIndicatorDrawer.stop();
        }
        mEndIndicatorDrawer.reset();
        ViewCompat.postOnAnimation(this, mEndIndicatorDrawer);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOnScrollListener(this);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPositionData();
    }

    private void initPositionData() {
        int height = getMeasuredHeight();
        mHeadIndicatorMaxHeight = height / 10;
        if (mHeadIndicatorHeight == Integer.MAX_VALUE) {
            mHeadIndicatorHeight = -mHeadIndicatorMaxHeight;
        }

        mEndIndicatorMinHeight = height - height / 10;
        if (mEndIndicatorHeight == Integer.MAX_VALUE) {
            mEndIndicatorHeight = height;
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        adapter.registerDataSetObserver(mLoadStateObserver);
    }

    @Override
    protected void onDetachedFromWindow() {
        getAdapter().unregisterDataSetObserver(mLoadStateObserver);
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        synchronized (mState) {
            if (mState == State.RESET && firstVisibleItem + visibleItemCount >= totalItemCount) {
                if (mLoadListener != null) {
                    boolean loading = mLoadListener.load(false);
                    if (loading) {
                        updateState(State.END_LOADING);
                    }
                }
            }
        }
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isFirstItemVisible()) {
                    mLastMotionX = mInitialMotionX = ev.getX();
                    mLastMotionY = mInitialMotionY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isStateForPull() && isFirstItemVisible()) {
                    float x = ev.getX();
                    float y = ev.getY();

                    float diff = mInitialMotionY - y;
                    float absDiff = Math.abs(diff);
                    float oppositeDiff = Math.abs(mInitialMotionX - x);

                    if (diff < 0 && absDiff > mTouchSlop && absDiff > oppositeDiff) {
                        mPullDown = y > mLastMotionY;
                        mLastMotionX = x;
                        mLastMotionY = y;
                        mIsBeingDragged = true;
                        pullEvent();
                        return true;
                    } else {
                        if (mIsBeingDragged) {
                            mIsBeingDragged = false;
                            updateState(State.RESET);
                            return false;
                        }
                    }
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mIsBeingDragged) {
                        if (mState == State.PULL_TO_REFRESH) {
                            updateState(State.RESET);
                        } else if (mState == State.RELEASE_TO_REFRESH) {
                            updateState(State.HEAD_LOADING);
                        }
                        mIsBeingDragged = false;
                        return false;
                    }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    private void pullEvent() {
        float diffY = (mLastMotionY - mInitialMotionY) / FRICTION;
        if (diffY < mHeadIndicatorMaxHeight) {
            updateState(State.PULL_TO_REFRESH);
        } else {
            updateState(State.RELEASE_TO_REFRESH);
        }
    }

    private void drawIndicator(Canvas canvas) {
        for (Map.Entry<Position, IndicatorDrawer> entry : mIndicatorDrawers.entrySet()) {
            IndicatorDrawer drawer = entry.getValue();
            if (drawer != null && drawer.needDraw()) {
                drawer.draw(canvas);
            }
        }
    }

    private boolean isFirstItemVisible() {
        if (getFirstVisiblePosition() <= 1) {
            final View firstVisibleChild = getChildAt(0);
            if (firstVisibleChild != null) {
                return firstVisibleChild.getTop() >= getTop();
            }
        }
        return false;
    }

    private boolean isStateForPull() {
        return mState != State.END_LOADING && mState != State.HEAD_LOADING;
    }

    public abstract class IndicatorDrawer implements Runnable {

        protected static final int RADIUS = 30;
        protected long mStartTime = -1;
        protected Interpolator mInterpolator = new AccelerateInterpolator();
        protected boolean mContinueRunning = true;
        protected boolean mDrawingIndicator = false;
        protected int mAngleInterval = 5;
        protected int mAngle;

        abstract boolean needDraw();
        abstract void draw(Canvas canvas);
        abstract void computePosition();
        abstract boolean animationPolicy();

        @Override
        public void run() {
            if (!mContinueRunning) {
                return;
            }
            invalidate();
            if (animationPolicy()) {
                mDrawingIndicator = true;
                ViewCompat.postOnAnimation(PullToRefreshListView.this, this);
            } else {
                mDrawingIndicator = false;
            }
        }

        public void invalidate() {
            computePosition();
            PullToRefreshListView.this.invalidate();
        }

        public void stop() {
            mContinueRunning = false;
        }

        public void reset() {
            mContinueRunning = true;
            mStartTime = System.currentTimeMillis();
        }
    }

    final class EndIndicatorDrawer extends IndicatorDrawer  {

        @Override
        public boolean needDraw() {
            return mDrawingIndicator;
        }

        @Override
        public void draw(Canvas canvas) {
            int width = getWidth();
            String loadingStr = getResources().getString(R.string.loading);
            TextPaint paint = new TextPaint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(35);
            Paint.FontMetrics fm = paint.getFontMetrics();
            int textHeight =  (int) Math.ceil(fm.descent - fm.top);
            float left = width / 2 - textHeight - RADIUS;
            float top = mEndIndicatorHeight - textHeight - RADIUS;
            float right = left + RADIUS * 2 + textHeight * 2;
            float bottom = top + RADIUS * 2 + textHeight * 2;
            canvas.save();
            Path path = new Path();
            path.addCircle(width / 2, mEndIndicatorHeight, RADIUS, Path.Direction.CW);
            canvas.clipRect(left, top, right, bottom);
            canvas.rotate(mAngle, left + RADIUS + textHeight, top + RADIUS + textHeight);
            canvas.drawTextOnPath(loadingStr, path, 0, 0, paint);
            canvas.restore();
        }

        @Override
        public void computePosition() {
            long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / ANIMATION_DURATION;
            normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
            final int deltaY = Math.round((getMeasuredHeight() - mEndIndicatorMinHeight)
                    * mInterpolator.getInterpolation(normalizedTime / 1000f));
            if (mState == State.END_LOADING) {
                mEndIndicatorHeight = getMeasuredHeight() - deltaY;
            } else if (mState == State.RESET) {
                mEndIndicatorHeight = mEndIndicatorMinHeight + deltaY;
            }
            mAngle += mAngleInterval;
            if (mAngle == 360) {
                mAngle = 0;
            }
        }

        @Override
        boolean animationPolicy() {
            return mState == State.END_LOADING || (mState == State.RESET && mEndIndicatorHeight <
                    getMeasuredHeight());
        }
    }

    final class HeadIndicatorDrawer extends IndicatorDrawer {

        @Override
        public boolean needDraw() {
            return (mState == State.PULL_TO_REFRESH && mHeadIndicatorHeight > 0) || mDrawingIndicator;
        }

        @Override
        public void draw(Canvas canvas) {
            int width = getWidth();
            String loadingStr = getResources().getString(R.string.pull_to_refresh);
            TextPaint paint = new TextPaint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(35);
            Paint.FontMetrics fm = paint.getFontMetrics();
            int textHeight =  (int) Math.ceil(fm.descent - fm.top);
            float left = width / 2 - textHeight - RADIUS;
            float top = mHeadIndicatorHeight - textHeight - RADIUS;
            float right = left + RADIUS * 2 + textHeight * 2;
            float bottom = top + RADIUS * 2 + textHeight * 2;
            canvas.save();
            Path path = new Path();
            path.addCircle(width / 2, mHeadIndicatorHeight, RADIUS, Path.Direction.CW);
            canvas.clipRect(left, top, right, bottom);
            canvas.rotate(mAngle, left + RADIUS + textHeight, top + RADIUS + textHeight);
            canvas.drawTextOnPath(loadingStr, path, 0, 0, paint);
            canvas.restore();
        }

        @Override
        public void computePosition() {
            if (mState == State.RESET) {
                long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / ANIMATION_DURATION;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
                final int deltaY = Math.round(mHeadIndicatorHeight * mInterpolator.getInterpolation(normalizedTime /
                        1000f));
                mHeadIndicatorHeight = mHeadIndicatorHeight - deltaY;
            } else if (mState == State.PULL_TO_REFRESH) {
                float diffY = mLastMotionY - mInitialMotionY;
                mHeadIndicatorHeight = (int) (Math.max(diffY, 0) / FRICTION);
            } else if (mState == State.RELEASE_TO_REFRESH) {
                mHeadIndicatorHeight = mHeadIndicatorMaxHeight;
            }
            if (mPullDown) {
                mAngle += mAngleInterval;
            } else {
                mAngle -= mAngleInterval;
            }
            if (mAngle == 360) {
                mAngle = 0;
            }
        }

        @Override
        boolean animationPolicy() {
            return (mState == State.RESET && mHeadIndicatorHeight > 0) || mState == State.HEAD_LOADING;
        }

        @Override
        public void reset() {
            super.reset();
        }
    }
}
