package org.bean.jandan.fragment;

import org.bean.jandan.adapter.PictureAdapter;
import org.bean.jandan.model.PictureResult;

/**
 * Created by liuyulong@yixin.im on 2015/4/30.
 */
public abstract class BaseRecycleViewPicFragment extends BaseRecycleViewNetFragment<PictureResult, PictureAdapter> {

    @Override
    protected PictureAdapter configAdapter() {
        return new PictureAdapter(getActivity());
    }

    @Override
    protected Class getResultClazz() {
        return PictureResult.class;
    }

}
