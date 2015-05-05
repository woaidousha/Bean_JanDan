package org.bean.jandan.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */
public class StringUtil {

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
