package com.lcb.study.vipcourseuitest.animation.animator;

/**
 * 自定义类型估值器
 */
public class MyFloatEvaluator {
    /**
     *
     * @param fraction 百分比
     * @param startValue 起始值
     * @param endValue 结束值
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

}
