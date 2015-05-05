package org.bean.jandan.common.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuyulong@yixin.im on 2015/4/27.
 */
public class CommonAdapter extends BaseAdapter implements IViewReclaimer {

    protected Context mContext;
    protected Fragment mFragment;
    private final List<?> mItems;
    private final AdapterDelegate mDelegate;
    private final LayoutInflater mLayoutInflater;
    private final Map<Class<?>, Integer> mViewTypes;
    private Object mTag;
    private boolean mutable;
    private Set<IScrollStateListener> mListeners;

    public CommonAdapter(Context context, List<?> items, AdapterDelegate delegate) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        mDelegate = delegate;
        mViewTypes = new HashMap<>();
        mListeners = new HashSet<>();
    }

    public CommonAdapter(Fragment fragment, List<?> items, AdapterDelegate delegate) {
        this(fragment.getActivity(), items, delegate);
        mFragment = fragment;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position < getCount() ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return mDelegate.enabled(position);
    }

    public List<?> getItems() {
        return mItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent, true);
    }

    public View getView(int position, View convertView, ViewGroup parent, boolean needRefresh) {
        if (convertView == null) {
            convertView = viewAtPosition(position);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.setPosition(position);
        if (needRefresh) {
            holder.refresh(getItem(position));
        }

        if (holder instanceof IScrollStateListener) {
            mListeners.add((IScrollStateListener) holder);
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mDelegate.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (getViewTypeCount() == 1) {
            return 0;
        }

        Class<?> clazz = mDelegate.viewHolderAtPosition(position);
        if (mViewTypes.containsKey(clazz)) {
            return mViewTypes.get(clazz);
        } else {
            int type = mViewTypes.size();
            if (type < getViewTypeCount()) {
                mViewTypes.put(clazz, type);
                return type;
            }
            return 0;
        }
    }

    public View viewAtPosition(int position) {
        ViewHolder holder = null;
        View view = null;
        Class<?> viewHolder = mDelegate.viewHolderAtPosition(position);
        try {
            holder = (ViewHolder) viewHolder.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view = holder.getView(mLayoutInflater);
        holder.setContext(mContext);
        holder.setFragment(mFragment);
        view.setTag(holder);
        return view;
    }

    @Override
    public void reclaimView(View view) {
        if (view == null) {
            return;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null) {
            holder.reclaim();
            mListeners.remove(holder);
        }
    }
}
