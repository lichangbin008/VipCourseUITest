package com.lcb.study.viewgroupevent.listener;

import com.lcb.study.viewgroupevent.MotionEvent;
import com.lcb.study.viewgroupevent.View;

/**
 * Created by ${lichangbin} on 2020/7/14.
 */
public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
