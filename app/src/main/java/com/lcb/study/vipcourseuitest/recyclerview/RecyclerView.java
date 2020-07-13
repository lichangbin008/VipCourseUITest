package com.lcb.study.vipcourseuitest.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
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

    private  int maximumVelocity;

    private  int minimumVelocity;

    private Flinger flinger;
    private VelocityTracker velocityTracker;



    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
//    点击    28 -40  滑动
        this.flinger = new Flinger(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.minimumVelocity = configuration.getScaledMinimumFlingVelocity();
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
        scrollY = 0;
        firstRow = 0;
        needRelayout = true;
        requestLayout();
    }

    //初始化2 子控件摆放，比较耗性能
    //设置adapter，父容器尺寸变化
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //防止调用次数过多
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
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

            //                第一行不是从0开始
            top = -scrollY;
            for (int i = 0; i < rowCount && top < height; i++) {
                bottom = top + heights[i];
                ViewHolder viewHolder = makeAndStep(i, 0, top, width, bottom);
                viewList.add(viewHolder);
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

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        //滑动
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int y2 = (int) event.getRawY();
                int diff = (int) (currentY - event.getRawY());
                scrollBy(0, diff);
            }
            case MotionEvent.ACTION_UP:{
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);

                int velocityY = (int) velocityTracker.getYVelocity();

                int initY = scrollY + sumArray(heights, 1, firstRow);
                int maxY = Math.max(0, sumArray(heights, 0, heights.length) - height);
//                判断是否开启 惯性滑动
                if (Math.abs( velocityY) > minimumVelocity) {
//                        线程  ---》自己看线程
                    flinger.start(0,initY,0,velocityY,0,maxY);
                }else {

                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }

                }
                break;
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

        //     scrollY取值   0 ---- 屏幕 的高度   0---无限大   2
//修正一下  内容的总高度 是他的边界值
        scrollY = scrollBounds(scrollY, firstRow, heights, height);

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
        } else if (scrollY < 0) {
            //            往下滑
            while (!viewList.isEmpty() && getFilledHeight() - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }

            while (0 > scrollY) {
                ViewHolder viewHolder = obtainView(firstRow - 1, width, heights[0]);
                viewList.add(0, viewHolder);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
        }

//        上滑        下一节课  摆放  下滑  +极限值判断  +  惯性滑动

        //        重新对一个子控件进行重新layout
        repositionViews();
    }

    /**
     * 重新摆放子控件
     */
    private void repositionViews() {
        int left, top, right, bottom, i;
        top =  - scrollY;
        i = firstRow;
        for (ViewHolder viewHolder   : viewList) {
            bottom = top + heights[i++];
            viewHolder.itemView.layout(0, top, width, bottom);
            top = bottom;
        }
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

    private int scrollBounds(int scrollY, int firstRow, int sizes[], int viewSize) {
        if (scrollY > 0) {
            Log.i(TAG, " 上滑 scrollBounds: scrollY  " + scrollY + "  各项之和  " + sumArray(sizes, firstRow, sizes.length - firstRow) + "  receryView高度  " + viewSize);
            //            往上滑  bug +
            if (sumArray(sizes, firstRow, sizes.length - firstRow) - scrollY >viewSize ) {
                scrollY = scrollY;
            }else {
                scrollY = sumArray(sizes, firstRow, sizes.length - firstRow) - viewSize;
            }
        }else {
            //            往下滑  y  firstRow= 0    -
            scrollY = Math.max(scrollY, -sumArray(sizes,0,firstRow));  //=0
//            scrollY = Math.max(scrollY, 0);  //=
            Log.i(TAG, "下滑  scrollBounds: scrollY  " + scrollY + "  各项之和  " + (-sumArray(sizes,0,firstRow)) );
        }
        return scrollY;
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

    class Flinger implements Runnable {
        //        scrollBy   （移动的偏移量)  而不是速度
        private Scroller scroller;
        //
        private int initY;

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY){
            scroller.fling(initX, initY, initialVelocityX
                    , initialVelocityY, 0, maxX, 0, maxY);
            this.initY = initY;
            post(this);
        }
        Flinger(Context context) {
            scroller = new Scroller(context);

        }
        @Override
        public void run() {
            if (scroller.isFinished()) {
                return;
            }

            boolean more=scroller.computeScrollOffset();
//
            int y = scroller.getCurrY();
            int diffY = initY - y;
            if (diffY != 0) {
                scrollBy(0, diffY);
                initY = y;
            }
            if (more) {
                post(this);
            }
        }

        boolean isFinished() {
            return scroller.isFinished();
        }
    }
}
