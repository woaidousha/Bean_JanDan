package org.bean.jandan.fragment;

import android.os.Bundle;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bean.jandan.adapter.PicCommentsAdapter;
import org.bean.jandan.common.C;
import org.bean.jandan.common.adapter.CommonRecycleAdapter;
import org.bean.jandan.common.net.HttpUtil;
import org.bean.jandan.model.PicCommentsResult;

import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/5/14.
 */
public class CommentsFragment extends BaseRecycleViewNetFragment<PicCommentsResult>{

    private String mCommentId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        mCommentId = bundle.getString(C.Extra.TAG_COMMENT_ID);
    }

    @Override
    protected CommonRecycleAdapter configAdapter() {
        return new PicCommentsAdapter(getActivity(), getDataSource());
    }

    @Override
    protected String url() {
        return C.URL.PIC.URL_COMMENT_LIST + mCommentId;
    }

    @Override
    protected Request buildRequest(boolean head) {
        return HttpUtil.buildReq(url(), null);
    }

    @Override
    public PicCommentsResult parseResult(Response response, Class<PicCommentsResult> classOfT) throws IOException {
        PicCommentsResult result = null;
        String json = response.body().string();
        result = PicCommentsResult.fromJson(json);
        return result;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
