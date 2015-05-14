package org.bean.jandan.fragment;

import org.bean.jandan.adapter.PictureAdapter;
import org.bean.jandan.common.C;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.model.PictureResult;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class WltFragment extends BaseRecycleViewNetFragment<PictureResult> {
    @Override
    protected String url() {
        return C.URL.PIC.WLT;
    }

    @Override
    protected CommonRecycleAdapter configAdapter() {
        return new PictureAdapter(getActivity());
    }
}
