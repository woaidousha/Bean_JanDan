package org.bean.jandan.common.util;

/**
 * Created by liuyulong@yixin.im on 2015/4/29.
 */
public class DebugUtil {

    public static void sleep(long time) {
        if (DebugLog.isRelease()) {
            return;
        }

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
