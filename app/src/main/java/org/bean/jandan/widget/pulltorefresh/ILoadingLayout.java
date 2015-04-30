package org.bean.jandan.widget.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 *
 * 加载时所显示view所需接口
 */
public interface ILoadingLayout {

    /**
     * Set the Last Updated Text. This displayed under the main label when
     * Pulling
     *
     * @param label - Label to set
     */
    public void setLastUpdatedLabel(CharSequence label);

    /**
     * Set the drawable used in the loading layout. This is the same as calling
     * <code>setLoadingDrawable(drawable, Mode.BOTH)</code>
     *
     * @param drawable - Drawable to display
     */
    public void setLoadingDrawable(Drawable drawable);

    /**
     * Set Text to show when the Widget is being Pulled
     * <code>setPullLabel(releaseLabel, Mode.BOTH)</code>
     *
     * @param pullLabel - CharSequence to display
     */
    public void setPullLabel(CharSequence pullLabel);

    /**
     * Set Text to show when the Widget is refreshing
     * <code>setRefreshingLabel(releaseLabel, Mode.BOTH)</code>
     *
     * @param refreshingLabel - CharSequence to display
     */
    public void setRefreshingLabel(CharSequence refreshingLabel);

    /**
     * Set Text to show when the Widget is being pulled, and will refresh when
     * released. This is the same as calling
     * <code>setReleaseLabel(releaseLabel, Mode.BOTH)</code>
     *
     * @param releaseLabel - CharSequence to display
     */
    public void setReleaseLabel(CharSequence releaseLabel);

    /**
     * Set's the Sets the typeface and style in which the text should be
     * displayed. Please see
     * {@link android.widget.TextView#setTypeface(Typeface)
     * TextView#setTypeface(Typeface)}.
     */
    public void setTextTypeface(Typeface tf);

}
