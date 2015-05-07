package org.bean.jandan.widget.helper;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.bean.jandan.widget.OnDoubleClickListener;

/**
 * Created by liuyulong@yixin.im on 2015/5/7.
 */
public class DoubleClickHelper {

    public static void addDoubleClick(final View view, final OnDoubleClickListener listener) {
        final GestureDetector gd = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (listener != null) {
                    listener.onDoubleClick(view);
                }
                return true;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        });
    }

}
