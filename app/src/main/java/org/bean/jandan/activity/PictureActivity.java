package org.bean.jandan.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Transition;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.ImageFetchHelper;
import org.bean.jandan.common.C;
import org.bean.jandan.common.util.URLUtil;
import org.bean.jandan.model.SinglePicture;

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

    public static void start(Context context, SinglePicture picture) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(C.Extra.TAG_SINGLE_PICTURE, picture);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private SimpleDraweeView mPicutre;

    private Uri mData;
    private SinglePicture mSinglePicture;
    private boolean mIsGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processIntent();
        loadPicture();
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
        mSinglePicture = (SinglePicture) intent.getSerializableExtra(C.Extra.TAG_SINGLE_PICTURE);
        if (mData == null && mSinglePicture == null) {
            finish();
            return;
        }
        if (mData == null) {
            mData = mSinglePicture.getPicUri();
        }

        mIsGif = URLUtil.isGifUrl(mData);
    }

    private void loadPicture() {
        ImageFetchHelper.fetchRecyclerViewImage(mPicutre, mData);
    }

    @Override
    public void onClick(View v) {
        if (v == mPicutre) {
            finish();
        }
    }

    class TransitionAdapter implements Transition.TransitionListener {
        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    }
}
