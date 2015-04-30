package org.bean.jandan.activity;

import android.support.v4.app.Fragment;

import org.bean.jandan.R;
import org.bean.jandan.fragment.OOXXFragment;
import org.bean.jandan.fragment.WltFragment;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public enum  MainMenu {

    WLT(R.string.main_list_wlt, WltFragment.class),
    OOXX(R.string.main_list_ooxx, OOXXFragment.class);

    public final int mLabelRes;
    public final Class<? extends Fragment> mFragmentClazz;
    public Fragment mFragment;

    private MainMenu(int labelRes, Class<? extends Fragment> clazz) {
        this.mLabelRes = labelRes;
        this.mFragmentClazz = clazz;
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
}
