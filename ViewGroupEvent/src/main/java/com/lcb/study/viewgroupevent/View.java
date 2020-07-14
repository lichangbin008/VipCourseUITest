package com.lcb.study.viewgroupevent;

/**
 * Created by ${lichangbin} on 2020/7/14.
 */
public class View {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isContainer(int x, int y) {

        if (x >= left && x < right && y >= top && y < bottom) {
            return true;
        }
        return false;
    }

    protected boolean onTouchEvent(MotionEvent event) {
//        if (onClickListener != null) {
//            onClickListener.onClick(this);
//            return true;
//        }
//        return false;
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return onTouchEvent(motionEvent);
    }
}
