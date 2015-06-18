package org.bean.jandan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.bean.jandan.R;

/**
 * Created by liuyulong@yixin.im on 2015/6/12.
 */
public class Main2Activity extends BaseColorActivity {

    private SmartTabLayout mPagerTab;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;

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
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mPagerTab.setViewPager(mViewPager);
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
