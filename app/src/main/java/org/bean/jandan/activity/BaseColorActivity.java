package org.bean.jandan.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.bean.jandan.R;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class BaseColorActivity extends ActionBarActivity {

    SystemBarTintManager mTintManager;

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
        super.setContentView(layoutResID);
        View contentView = findViewById(android.R.id.content);
        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + mTintManager.getConfig()
                .getStatusBarHeight() +
                mTintManager.getConfig().getActionBarHeight(), contentView.getPaddingRight(), contentView.getPaddingBottom());
        View actionbar = findViewById(R.id.action_bar_container);
        actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
    }
}
