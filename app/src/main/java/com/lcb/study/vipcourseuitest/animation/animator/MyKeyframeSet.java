package com.lcb.study.vipcourseuitest.animation.animator;


import java.util.Arrays;
import java.util.List;

/**
 * 存储一个动画的关键帧集**
 */
public class MyKeyframeSet {

    //    类型估值器
    MyFloatEvaluator mEvaluator;
    private List<MyFloatKeyframe> mKeyFrames;

    MyFloatKeyframe mFirstKeyFrame;

    public MyKeyframeSet(MyFloatKeyframe... keyframes) {
        mKeyFrames = Arrays.asList(keyframes);
        mFirstKeyFrame = keyframes[0];
        mEvaluator = new MyFloatEvaluator();
    }

    //初始化关键帧集合
    public static MyKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        MyFloatKeyframe keyframes[] = new MyFloatKeyframe[Math.max(numKeyframes, 2)];
        if (numKeyframes == 1) {
            keyframes[0] = new MyFloatKeyframe(0, values[0]);//第一个关键帧比较特殊
            keyframes[1] = new MyFloatKeyframe(1f, values[0]);
        } else {
            keyframes[0] = new MyFloatKeyframe(0, values[0]);//第一个关键帧比较特殊
            for (int i = 1; i < numKeyframes; i++) {
                keyframes[i] = new MyFloatKeyframe((float) i / (numKeyframes - 1), values[i]);
            }
        }

        return new MyKeyframeSet(keyframes);
    }

    public Object getValue(float fraction) {
        MyFloatKeyframe prevKeyframe = mFirstKeyFrame;
        for (int i = 1; i < mKeyFrames.size(); ++i) {
            MyFloatKeyframe nextKeyFrame = mKeyFrames.get(i);
            if (fraction < nextKeyFrame.getFraction()) {
                //这一个区间
                return mEvaluator.evaluate(fraction, prevKeyframe.getValue(), nextKeyFrame.getValue());
            }
            prevKeyframe = mKeyFrames.get(i);
        }
        return null;
    }
}
