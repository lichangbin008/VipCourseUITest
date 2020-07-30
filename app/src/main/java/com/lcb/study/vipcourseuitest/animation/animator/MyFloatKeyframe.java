package com.lcb.study.vipcourseuitest.animation.animator;

/**
 * 关键帧 保存着 某一时刻的具体状态    初始化的时候
 */
public class MyFloatKeyframe {

    //关键帧对应的百分比，传入值
    float mFraction;

    //执行上面的百分比，对应的具体值
    float mValue;

    Class mValueType;

    public MyFloatKeyframe(float mFraction, float mValue) {
        this.mFraction = mFraction;
        this.mValue = mValue;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }

    public float getFraction() {
        return mFraction;
    }
}
