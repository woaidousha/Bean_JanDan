package org.bean.jandan.common.util;

import android.graphics.Color;

/**
 * Created by liuyulong@yixin.im on 2015/5/4.
 */
public class ColorUtil {

    public static int changeColorAlpha(int alpha, int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

    public static String Color2String(int color) {
        String A = Integer.toHexString(Color.alpha(color));
        A = A.length() < 2 ? ('0' + A) : A;
        String R = Integer.toHexString(Color.red(color));
        R = R.length() < 2 ? ('0' + R) : R;
        String B = Integer.toHexString(Color.blue(color));
        B = B.length() < 2 ? ('0' + B) : B;
        String G = Integer.toHexString(Color.green(color));
        G = G.length() < 2 ? ('0' + G) : G;
        return '#'+ A + R + B + G;
    }
}
