package org.bean.jandan.fragment;

import org.bean.jandan.adapter.JokeAdapter;
import org.bean.jandan.common.C;
import org.bean.jandan.model.JokeResult;

/**
 * Created by liuyulong@yixin.im on 2015/5/6.
 */
public class JokeFragment extends BaseRecycleViewNetFragment<JokeResult> {

    @Override
    protected String url() {
        return C.URL.DUANZI;
    }

    @Override
    protected JokeAdapter configAdapter() {
        return new JokeAdapter(getActivity());
    }

    @Override
    protected Class getResultClazz() {
        return JokeResult.class;
    }
}
