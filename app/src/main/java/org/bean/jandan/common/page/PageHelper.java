package org.bean.jandan.common.page;

import android.text.TextUtils;

import com.squareup.okhttp.Request;

/**
 * Created by liuyulong@yixin.im on 2015/5/13.
 */
public class PageHelper {

    public static Request createPageReq(Page page, boolean isFrist) {
        if (page == null) {
            return null;
        }
        String url;
        if (isFrist) {
            url = page.firstPage();
        } else {
            url = page.nextPage();
        }
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request.Builder builder = new Request.Builder();
        return builder.url(url)
                      .tag(page.isFirstPage(url))
                      .build();
    }

    public static Request firstPageReq(Page page) {
        return createPageReq(page, true);
    }

    public static Request nextPageReq(Page page) {
        return createPageReq(page, true);
    }
}
