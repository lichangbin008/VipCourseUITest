package com.lcb.study.vipcourseuitest.animation.animator;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * 自定义动画
 */
public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {

    //需要一定时间，要弱引用
    private WeakReference<View> target;

    MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;

    long mStartTime = -1;

    private long mDuration = 0;

    private float index = 0;
    private TimeInterpolator interpolator;
    private boolean repeat = false;

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public MyObjectAnimator(View view, String propertyName, float... values) {
        this.target = new WeakReference<View>(view);
        myFloatPropertyValuesHolder = MyFloatPropertyValuesHolder.ofFloat(propertyName, values);
    }

    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {
        MyObjectAnimator myObjectAnimator = new MyObjectAnimator(view, propertyName, values);
        return myObjectAnimator;
    }

    //VSYNC信号 系统源码
    public void start() {
        //一旦开始动画，就开始反射
        myFloatPropertyValuesHolder.setupSetter(target);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    //每隔16ms
    @Override
    public boolean doAnimationFrame(long currentTime) {

        //次数百分比的计算
        float total = mDuration / 16;

        //时间百分比
        float fraction = (index++) / total;

        //差值器去改变百分比
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }

        //无线循环动画
        if (index > total) {
            if (repeat) {
                index = 0;
            } else {
                VSYNCManager.getInstance().remove(this);
            }
        }

        myFloatPropertyValuesHolder.setAnimatedValue(target.get(), fraction);
        return false;
    }
}
