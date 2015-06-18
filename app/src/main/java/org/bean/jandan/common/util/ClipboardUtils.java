package org.bean.jandan.common.util;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * Created by liuyulong@yixin.im on 2015/6/18.
 */
public class ClipboardUtils {


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void copyToClipboard(Context context, String planText) {
        if(planText == null) {
            return;
        }
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, planText);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(planText);
        }
    }
}
