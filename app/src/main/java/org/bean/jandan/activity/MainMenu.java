package org.bean.jandan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.bean.jandan.BeanApp;
import org.bean.jandan.R;
import org.bean.jandan.fragment.JokeFragment;
import org.bean.jandan.fragment.NewFreshFragment;
import org.bean.jandan.fragment.OOXXFragment;
import org.bean.jandan.fragment.WltFragment;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public enum  MainMenu {

    WLT(R.string.main_list_wlt, WltFragment.class),
    OOXX(R.string.main_list_ooxx, OOXXFragment.class),
    JOKE(R.string.main_list_joke, JokeFragment.class),
    POSTS(R.string.main_list_post, NewFreshFragment.class),
    ;

    public final int mLabelRes;
    public final Class<? extends Fragment> mFragmentClazz;
    public Fragment mFragment;

    MainMenu(int labelRes, Class<? extends Fragment> clazz) {
        this.mLabelRes = labelRes;
        this.mFragmentClazz = clazz;
    }

    public String getLabel() {
        return BeanApp.getApp().getString(mLabelRes);
    }

    public Fragment getFragment() {
        if (mFragment == null) {
            try {
                mFragment = mFragmentClazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mFragment;
    }

    public static void initMenuFragments(FragmentTransaction transaction) {
        for (MainMenu menu : MainMenu.values()) {
            transaction.add(R.id.fragment_container, menu.getFragment(), menu.name());
            transaction.hide(menu.getFragment());
        }
        transaction.commitAllowingStateLoss();
    }

    public void showMenuFragment(FragmentTransaction transaction) {
        for (MainMenu menu : MainMenu.values()) {
            transaction.hide(menu.getFragment());
        }
        transaction.show(getFragment());
        transaction.commitAllowingStateLoss();
    }
}
