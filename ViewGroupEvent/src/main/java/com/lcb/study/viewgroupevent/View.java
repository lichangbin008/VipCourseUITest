package com.lcb.study.viewgroupevent;

import com.lcb.study.viewgroupevent.listener.OnClickListener;
import com.lcb.study.viewgroupevent.listener.OnTouchListener;

/**
 * Created by ${lichangbin} on 2020/7/14.
 */
public class View {

    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener onClickListener;

    public void setmOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

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


    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
//        System.out.println("View  dispatchTouchEvent");
        boolean result = false;
        if (mOnTouchListener != null && mOnTouchListener.onTouch(this, motionEvent)) {
            //执行这里面的代码
            result = true;

        }
        //不执行代码

        if (!result && onTouchEvent(motionEvent)) {
            result = true;
        }
        return result;
    }

    protected boolean onTouchEvent(MotionEvent event) {
        if (onClickListener != null) {
            onClickListener.onClick(this);
            return true;
        }
        return false;
    }
}
