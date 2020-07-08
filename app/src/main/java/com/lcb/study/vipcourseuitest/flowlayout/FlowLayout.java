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

        if (!isMeasure) {
            isMeasure = true;
        } else {
            //当前控件中子控件一行使用的宽度值
            int lineCountWidth = 0;
            //保存一行中最高的子控件的高度
            int lineMaxHeight = 0;

            //存储每个子控件的宽高
            int iChildWidth = 0;
            int iChileHeight = 0;
            //创建一行的容器
            List<View> viewList = new ArrayList<>();

            //遍历所有子控件
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);

                //先测量子控件
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
                iChildWidth = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                iChileHeight = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                //判断当前子控件的累加长度是否超出实际宽度
                if (iChildWidth + lineCountWidth > widthSize - getPaddingLeft() - getPaddingRight()) {//换行
                    //先处理换行之前的逻辑

                    //如果需要换行 我们就要保存一行的信息
                    //每次换行的时候都要比较当前行和前面行谁的宽度最大
                    childCountWidth = Math.max(lineCountWidth, childCountWidth);
                    //如果需要换行  要累加行高
                    childCountHeight += lineMaxHeight;
                    //把行高记录到集合中
                    listLineHeight.add(lineMaxHeight);
                    //把这一行的数据放进总容器
                    list.add(viewList);

                    //处理新换的一行
                    //把一行的容器 重新创建一个，相当于下一行
                    viewList = new ArrayList<>();

                    //将每一行的总长度  重新初始化
                    lineCountWidth = iChildWidth;

                    lineMaxHeight = iChileHeight;
                    //将高度也重新初始化
                    viewList.add(childAt);
                } else {//不换行
                    lineCountWidth += iChildWidth;
                    //对比每个子控件到底谁的高度最高
                    lineMaxHeight = Math.max(iChileHeight, lineMaxHeight);
                    //如果当前不需要换行  就将当前控件保存在一行中
                    viewList.add(childAt);
                }

                //这样做的原因是  之前的ifelse中 不会把最后一行的高度加进listLineHeight
                // 最后一行要特殊对待 不管最后一个item是不是最后一行的第一个item
                if (i == childCount - 1) {
                    //保存当前行信息
                    childCountWidth = Math.max(childCountWidth, lineCountWidth);
                    childCountHeight += lineMaxHeight;
                    listLineHeight.add(lineMaxHeight);
                    list.add(viewList);
                }
            }
        }

        //设置控件最终的大小
        int measureWidth = widthMode == MeasureSpec.EXACTLY ?
                widthSize : childCountWidth + getPaddingLeft() + getPaddingRight();
        int measureHeight = heightMode == MeasureSpec.EXACTLY ?
                heightSize : childCountHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放子控件的位置
        int left, top, bottom, right;
        //保存上一个控件的边距
        int countLeft = 0 + getPaddingLeft();
        //保存上一行的高度的边距
        int countTop = 0 + getPaddingTop();
        //遍历每所有行
        for (List<View> views : list) {
            //遍历每一行的控件
            for (View view : views) {
                //获取控件的属性对象
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                left = countLeft + params.leftMargin;
                top = countTop + params.topMargin;
                right = left + view.getMeasuredWidth();
                bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);

                countLeft += view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            }
            //获取到当前这一行的position
            int i = list.indexOf(views);
            countLeft = 0 + getPaddingLeft();
            countTop += listLineHeight.get(i);
        }
        list.clear();
        listLineHeight.clear();
    }
}
