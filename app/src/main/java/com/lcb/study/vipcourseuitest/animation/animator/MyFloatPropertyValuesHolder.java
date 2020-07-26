package com.lcb.study.vipcourseuitest.animation.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 持有目标属性Property、setter和getter方法、以及关键帧集合的类。
 */
public class MyFloatPropertyValuesHolder {

    MyKeyframeSet mKeyframes;

    //setScaleX Method
    Method mSetter = null;
    private String mPropertyName;

    //float
    Class mValueType;

    //属性，反射执行动画效果 scaleX setScaleX
    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        //初始化关键帧集合 初始化 method 方法
        mKeyframes = MyKeyframeSet.ofFloat(values);
        mPropertyName = propertyName;
    }

    public static MyFloatPropertyValuesHolder ofFloat(String propertyName, float... values){
        MyFloatPropertyValuesHolder myObjectAnimator = new MyFloatPropertyValuesHolder(propertyName, values);
        return myObjectAnimator;
    }

    public void setupSetter(WeakReference<View> target) {
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;
        try {
            //耗性能
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    //根据百分比，确认最终的状态值
    public void setAnimatedValue(View target, float fraction) {
        Object value = mKeyframes.getValue(fraction);
        try {
//            View.setScaleX(101px)
            mSetter.invoke(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
