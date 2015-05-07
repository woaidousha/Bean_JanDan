package org.bean.jandan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.bean.jandan.R;
import org.bean.jandan.widget.OnDoubleClickListener;
import org.bean.jandan.widget.helper.DoubleClickHelper;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class BaseColorActivity extends ActionBarActivity implements OnDoubleClickListener {

    SystemBarTintManager mTintManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintDrawable(getResources().getDrawable(R.drawable.status_bar_color));
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_color_activity);
        FrameLayout activityContent = (FrameLayout) findViewById(R.id.activity_content);
        View content = LayoutInflater.from(this).inflate(layoutResID, activityContent, true);
        View contentView = findViewById(android.R.id.content);
        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + mTintManager.getConfig()
                                                                                                       .getStatusBarHeight(), contentView
                .getPaddingRight(), contentView.getPaddingBottom());
        mToolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        mToolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        DoubleClickHelper.addDoubleClick(mToolbar, this);
        setSupportActionBar(mToolbar);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
