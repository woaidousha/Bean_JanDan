package org.bean.jandan.widget.pulltorefresh;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class LoadingLayout extends LinearLayout implements ILoadingLayout {

    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }

    @Override
    public void setLoadingDrawable(Drawable drawable) {

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }

    @Override
    public void setTextTypeface(Typeface tf) {

    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public void reset() {

    }
}
