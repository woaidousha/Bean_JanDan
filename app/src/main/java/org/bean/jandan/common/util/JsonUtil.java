package org.bean.jandan.common.util;

import com.google.gson.Gson;

/**
 * Created by liuyulong@yixin.im on 2015/4/28.
 */
public class JsonUtil {

    private static Gson gson = new Gson();

    public static Gson gson() {
        return gson;
    }

}
