package org.bean.jandan.common.util;

import android.net.Uri;
import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */
public class URLUtil {

    private static final String DEFAULT_ENCODING = "utf-8";

    public static void decode(String url, Map<String, String> values) {
        decode(url, values, null);
    }

    public static void decode(String url, Map<String, String> values, String encoding) {
        if (values == null) {
            return;
        }
        if (TextUtils.isEmpty(encoding)) {
            encoding = DEFAULT_ENCODING;
        }

        try {
            URL URL = new URL(url);
            String query = URL.getQuery();
            String querys[] = query.split("&");
            for (String s : querys) {
                String[] items = s.split("=");
                if (items.length != 2) {
                    continue;
                }
                values.put(items[0], items[1]);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isGifUrl(Uri uri) {
        return isGifUrl(uri == null ? "" : uri.toString());
    }

    public static boolean isGifUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.toLowerCase().endsWith("gif");
    }
}
