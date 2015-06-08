package org.bean.jandan.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import org.bean.jandan.R;
import org.bean.jandan.databinding.AboutActivityLayoutBinding;

/**
 * Created by liuyulong@yixin.im on 2015/6/5.
 */
public class AboutActivity extends BaseColorActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.about_activity_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutActivityLayoutBinding binding = (AboutActivityLayoutBinding) getViewDataBinding();
        binding.setAuthor("Mr.Bean");

        binding.setImageUri(R.mipmap.ic_launcher + "");
        try {
            binding.setVersion(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
