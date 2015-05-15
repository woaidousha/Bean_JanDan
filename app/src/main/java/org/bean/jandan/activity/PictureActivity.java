package org.bean.jandan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.ImageFetchHelper;

/**
 * Created by liuyulong@yixin.im on 2015/5/12.
 */
public class PictureActivity extends BaseColorActivity implements View.OnClickListener {

    public static void start(Context context, Uri data) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.setData(data);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private SimpleDraweeView mPicutre;

    private Uri mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processIntent();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.picture_activity_layout;
    }

    @Override
    protected void findViews() {
        mPicutre = (SimpleDraweeView) findViewById(R.id.picture);
        mPicutre.setOnClickListener(this);
    }

    private void processIntent() {
        Intent intent = getIntent();
        mData = intent.getData();
        if (mData == null) {
            finish();
            return;
        }
        ImageFetchHelper.fetchRecyclerViewImage(mPicutre, mData);
    }

    @Override
    public void onClick(View v) {
        if (v == mPicutre) {
            finish();
        }
    }
}
