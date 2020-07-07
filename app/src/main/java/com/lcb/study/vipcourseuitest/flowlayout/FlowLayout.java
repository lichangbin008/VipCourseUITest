package com.lcb.study.vipcourseuitest.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流式布局
 */
public class FlowLayout extends ViewGroup {

    //所有的子控件的容器
    List<List<View>> list = new ArrayList<>();
    //把每一行的行高存起来
    List<Integer> listLineHeight = new ArrayList<>();
    //防止测量多次
    private boolean isMeasure = false;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }

    //在被调用这个方法之前   它的父容器  已经把它的测量模式改成了当前控件的测量模式
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取到父容器 给我们的参考值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //获取到自己的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //保存当前控件的里面子控件的总宽高
        int childCountWidth = 0;
        int childCountHeight = 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
