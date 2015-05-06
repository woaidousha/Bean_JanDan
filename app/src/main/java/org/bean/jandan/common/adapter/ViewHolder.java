package org.bean.jandan.common.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public abstract class ViewHolder implements IScrollStateListener, IViewHolder {

    private Context mContext;
    private Fragment mFragment;
    protected int mPosition;

    protected View mView;

    public abstract int getResId();

    public ViewHolder() {

    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public View getView(LayoutInflater inflater) {
        int resId = getResId();
        mView = inflater.inflate(resId, null);
        inflate(mView);
        return mView;
    }

    public abstract void refresh(Object item);

    @Override
    public void reclaim() {

    }

    @Override
    public void onImmutable() {

    }
}
