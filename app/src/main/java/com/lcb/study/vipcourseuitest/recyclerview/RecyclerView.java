package com.lcb.study.vipcourseuitest.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义RecyclerView
 */
public class RecyclerView extends ViewGroup {

    private static final String TAG = "RecyclerView";
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

    //最小滑动距离
    private int touchSlop;
    //当前滑动的y值
    private int currentY;
    //y偏移量      内容偏移量
    private int scrollY;
    //view的弟一行  是占内容的几行
    private int firstRow = 0;


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
        ViewHolder viewHolder = recycledViewPool.getRecycledView(itemType);
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

    //滑动事件     点击事件    定向拦截       需 要 1  不需要 2

    //难  事件   迈步 onTouchEvent 隔多少时间   px
    //onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //滑动
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int y2 = (int) event.getRawY();
                int diff = (int) (currentY - event.getRawY());
                scrollBy(0, diff);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) ev.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //移动距离大于最小滑动距离才认为是滑动，不然按照点击事件处理
                int y2 = (int) Math.abs(currentY - ev.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
                break;
            }
        }
        return intercept;
    }

    //只对 canvas进行滚动
    //容器重新摆放每一个子控件
    @Override
    public void scrollBy(int x, int y) {
        //摆放
        scrollY += y;

        //上滑
        if (scrollY > 0) {
            while (scrollY > heights[firstRow]) {
                //上面个
                //扔到回收池
                //firstRow++
                if (!viewList.isEmpty()) {
                    removeView(viewList.remove(0));
                }
                scrollY -= heights[firstRow];
                firstRow++;
                Log.i(TAG, "移除一个元素 ");
            }
            while (getFilledHeight() < height) {
                //需要被添加  滑动   肯定不是第一屏数据
                int dataIndex = firstRow + viewList.size();
                ViewHolder viewHolder = obtainView(dataIndex, width, heights[dataIndex]);
                viewList.add(viewList.size(), viewHolder);
                Log.i(TAG, "添加一个元素 ");
            }
//            上滑 顶端 溢出一个元素         底部 可能 不会添加的情况
        }

//        上滑        下一节课  摆放  下滑  +极限值判断  +  惯性滑动
    }

    private int getFilledHeight() {

        return sumArray(heights, firstRow, viewList.size()) - scrollY;

    }

    //累加算法    firstIndex    firstIndex+count 结束  结果sum
    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    private void removeView(ViewHolder remove) {
        //放入缓冲池中
        recycledViewPool.putRecycledView(remove);
        removeView(remove.itemView);
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
