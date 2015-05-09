package org.bean.jandan.fragment;

import org.bean.jandan.adapter.PictureAdapter;
import org.bean.jandan.common.C;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.model.PictureResult;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class OOXXFragment extends BaseRecycleViewNetFragment<PictureResult> {
    @Override
    protected String url() {
        return C.URL.OOXX;
    }

    @Override
    protected CommonRecycleAdapter configAdapter() {
        return new PictureAdapter(getActivity());
    }
}
