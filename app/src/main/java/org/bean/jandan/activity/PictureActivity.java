package org.bean.jandan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.bean.jandan.R;
import org.bean.jandan.adapter.helper.ImageFetchHelper;
import org.bean.jandan.common.C;
import org.bean.jandan.common.cache.CacheManager;
import org.bean.jandan.common.cache.Cacheable;
import org.bean.jandan.model.SinglePicture;

import java.util.List;

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

    private ViewPager mContainer;
    private PictureAdapter mAdapter;

    private List<Cacheable> mPictures;
    int mOriginIndex;

    private SinglePicture mSinglePicture;

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
        mContainer = (ViewPager) findViewById(R.id.container);
        mContainer.setOffscreenPageLimit(5);
    }

    private void processIntent() {
        Intent intent = getIntent();
        mSinglePicture = (SinglePicture) intent.getSerializableExtra(C.Extra.TAG_SINGLE_PICTURE);
    }

    private void loadPicture() {
        mPictures = CacheManager.get().cache().get(mSinglePicture.getBaseKey());
        mAdapter = new PictureAdapter();
        mContainer.setAdapter(mAdapter);
        mOriginIndex = mPictures.indexOf(mSinglePicture);
        mContainer.setCurrentItem(mOriginIndex);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picture) {
            finish();
        }
    }

    class PictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPictures == null ? 0 : mPictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup view = (ViewGroup) LayoutInflater.from(PictureActivity.this)
                                                                     .inflate(R.layout.picture_item_layout,
                                                                             container, false);
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.picture);
            Cacheable picture = mPictures.get(position);
            if (SinglePicture.class.isInstance(picture)) {
                ImageFetchHelper.fetchRecyclerViewImage(draweeView, ((SinglePicture) picture).getPicUri());
            }
            view.setOnClickListener(PictureActivity.this);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
