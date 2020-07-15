package com.lcb.study.viewgroupevent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lichangbin} on 2020/7/14.
 */
public class ViewGroup extends View {

    //容器View
    List<View> childList = new ArrayList<>();

    private View[] mChildren = new View[0];

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = (View[]) childList.toArray(new View[childList.size()]);
    }

    //    事件分发的入口
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        //分发事件
        boolean handled = false;

        boolean intercepted = onInterceptTouchEvent(motionEvent);

        int actionMasked = motionEvent.getActionMasked();

        if (!intercepted && actionMasked != MotionEvent.ACTION_CANCEL) {
            //分发事件，子view，合适的x,y
            final View[] children = mChildren;
//                耗时  概率大一些
            for (int i = children.length - 1; i >= 0; i--) {
                View child = mChildren[i];

//                View不能够接收到事件
                if (!child.isContainer(motionEvent.getX(), motionEvent.getY())) {
                    continue;
                }
                //能接收到事件，分发事件
                if (dispatchTransformedTouchEvent(motionEvent, child)) {
                    //证明有子控件进行消费行为，不需要再遍历了
                    handled = true;
                    break;
                }
            }

        }

        if (intercepted) {
            handled = dispatchTransformedTouchEvent(motionEvent, null);
        }
        return handled;

//        onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    //分发处理 子控件  View
    //两个目的，child传值和不传值
    //传值不为null，分发给子view，坐标值位置的转换
    //传值为null，自己调用ontouchevent方法
    private boolean dispatchTransformedTouchEvent(MotionEvent motionEvent, View child) {
        boolean handled = false;
        //        当前View消费了
        if (child == null) {
            handled = super.dispatchTouchEvent(motionEvent);
        } else {
            handled = child.dispatchTouchEvent(motionEvent);
        }
        return handled;
    }

    private String name;

    @Override
    public String toString() {
        return "" + name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
