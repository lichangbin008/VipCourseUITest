package com.lcb.study.vipcourseuitest.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义RecyclerView
 */
public class RecyclerView extends ViewGroup {

    //协调Adapter和回收池之间的工作
    private Adapter adapter;

    private RecycledViewPool recycledViewPool;

    //当前RecyclerView的宽度
    private int width;
    //当前RecyclerView的高度
    private int height;
    // 不一样 简便   item 先把 每一个Item的高度先写死
    private int[] heights;
    //行数
    private int rowCount;

    //    缓存当前显示的Item
    private List<ViewHolder> viewList;

    //控制onLayout初始化次数
    private boolean needRelayout = true;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置适配器 初始化1
     *
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        needRelayout = true;
        recycledViewPool = new RecycledViewPool();
        requestLayout();
    }

    //初始化2 子控件摆放，比较耗性能
    //设置adapter，父容器尺寸变化
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //防止调用次数过多
        if (needRelayout || changed) {
            needRelayout = false;
            //走重新摆放

            //计算宽高
            if (adapter != null) {
                width = r - l;
                height = b - t;
            }

            rowCount = adapter.getItemCount();
            heights = new int[rowCount];
            for (int i = 0; i < rowCount; i++) {
                heights[i] = adapter.getHeight(i);
            }
            //摆放 假设有20000个
            //摆放View 生成View
            int left, top = 0, right, bottom;
            for (int i = 0; i < rowCount && top < height; i++) {
                bottom = top + heights[i];
                ViewHolder viewHolder = makeAndStep(i, 0, top, width, bottom);
                //                遍历条件
                top = bottom;
            }

        }
    }

    private ViewHolder makeAndStep(int row, int left, int top, int right, int bottom) {
        //产生ViewHolder
        ViewHolder viewHolder = obtainView(row, right - left, bottom - top);
        viewHolder.itemView.layout(left, top, right, bottom);
        return viewHolder;
    }

    private ViewHolder obtainView(int row, int width, int height) {
        //      类型  用户  1   架构 2
        int itemType = adapter.getItemViewType(row);
        //先从recycledViewPool中获取，如果没有再从adapter中获取
        //第一屏数据
        ViewHolder viewHolder = recycledViewPool.getRecyclerView(itemType);
        if (viewHolder == null) {
            viewHolder = adapter.onCreateViewHolder(this, itemType);
        }
        //更新数据，内存
        adapter.onBindViewHolder(viewHolder, row);
        viewHolder.setmItemViewType(itemType);
        viewHolder.itemView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        addView(viewHolder.itemView, 0);
        return viewHolder;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    interface Adapter<VH extends ViewHolder> {
        VH onCreateViewHolder(ViewGroup parent, int viewType);

        VH onBindViewHolder(VH viewHodler, int position);

        //Item的类型
        int getItemViewType(int position);

        int getItemCount();

        int getHeight(int index);
    }
}
