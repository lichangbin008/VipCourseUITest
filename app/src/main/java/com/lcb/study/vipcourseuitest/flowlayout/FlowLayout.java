package com.lcb.study.vipcourseuitest.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lichangbin} on 2020/7/7.
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
