package org.bean.jandan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.bean.jandan.R;
import org.bean.jandan.fragment.BaseRecycleViewNetFragment;

/**
 * Created by liuyulong@yixin.im on 2015/6/12.
 */
public class Main2Activity extends BaseColorActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private SmartTabLayout mPagerTab;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private FloatingActionButton mFab;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main2;
    }

    @Override
    public boolean showToolBar() {
        return false;
    }

    @Override
    protected void findViews() {
        mPagerTab = (SmartTabLayout) findViewById(R.id.pager_tab_strip);
        mViewPager = (ViewPager) findViewById(R.id.fragment_container);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mPagerTab.setViewPager(mViewPager);
        mPagerTab.setOnPageChangeListener(this);
    }

    private void attachFab() {
        BaseRecycleViewNetFragment fragment = (BaseRecycleViewNetFragment) mAdapter.getItem(mViewPager.getCurrentItem());
        mFab.attachToRecyclerView(fragment.getRecycleView());
    }

    @Override
    public void onClick(View v) {
        if (v == mFab) {
            BaseRecycleViewNetFragment fragment = (BaseRecycleViewNetFragment) mAdapter.getItem(mViewPager.getCurrentItem());
            fragment.goToTop();
            return;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        attachFab();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MainMenu.values()[position].getLabel();
        }

        @Override
        public Fragment getItem(int position) {
            return MainMenu.values()[position].getFragment();
        }

        @Override
        public int getCount() {
            return MainMenu.values().length;
        }
    }
}
