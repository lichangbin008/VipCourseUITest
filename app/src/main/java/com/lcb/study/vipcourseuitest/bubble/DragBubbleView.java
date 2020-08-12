package com.lcb.study.vipcourseuitest.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.lcb.study.vipcourseuitest.R;

/**
 * Created by ${lichangbin} on 2020/8/11.
 */
public class DragBubbleView extends View {

    // 容器控件  ------》onLayout  onMeasure   ---事件  拦截事件


//    容器控件  1   其他控件  自汇控件 2


//    onDraw      onTouchEvent


//状态   自定义    1  状态   几种状态
    /**
     * 气泡状态标志
     */

    /**
     * 静止      1   在    2   不在
     * <p>
     * 动态     气泡相连
     * 气泡默认状态--静止 onTouchEvent  改变状态  mBubbleState
     * onDraw  用  mBubbleState
     * <p>
     * <p>
     * 99顾客  今天---》
     */
    private final int BUBBLE_STATE_DEFAUL = 0;
    /**
     * 气泡相连
     */
    private final int BUBBLE_STATE_CONNECT = 1;

    /**
     * 气泡分离
     */
    private final int BUBBLE_STATE_APART = 2;
    /**
     * 气泡消失
     */
    private final int BUBBLE_STATE_DISMISS = 3;

    /**
     * 气泡状态
     */
    private int mBubbleState = BUBBLE_STATE_DEFAUL;

    /**
     * 是否在执行气泡爆炸动画
     */
    private boolean mIsBurstAnimStart = false;

    //文字
    private Paint mTextPaint;
    /**
     * 气泡的画笔
     */
    private Paint mBubblePaint;

    //自定义变量
    /**
     * 气泡半径
     */
    private float mBubbleRadius;
    /**
     * 气泡消息文字
     */
    private String mTextStr;
    /**
     * 气泡消息文字颜色
     */
    private int mTextColor;

    /**
     * 气泡消息文字大小
     */
    private float mTextSize;

    /**
     * 气泡颜色
     */
    private int mBubbleColor;

    /**
     * 不动气泡的圆心
     */
    private PointF mBubStillCenter;
    /**
     * 可动气泡的圆心
     */
    private PointF mBubMoveableCenter;

    //文本绘制区域
    private Rect mTextRect;

    /**
     * 两气泡圆心距离
     */
    private float mDist;

    // 自定义   声明成全局

    /**
     * 可动气泡的半径
     */
    private float mBubMoveableRadius;

    /**
     * 贝塞尔曲线path
     */
    private Path mBezierPath;
    /**
     * 气泡相连状态最大圆心距离
     */
    private float mMaxDist;
    /**
     * 不动气泡的半径
     */
    private float mBubStillRadius;


    /**
     * 气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.drawable.burst_1, R.drawable.burst_2
            , R.drawable.burst_3, R.drawable.burst_4, R.drawable.burst_5};


    /**
     * 气泡爆炸的bitmap数组
     */
    private Bitmap[] mBurstBitmapsArray;
    //爆炸绘制区域
    private Rect mBurstRect;

    /**
     * 当前气泡爆炸图片index
     */
    private int mCurDrawableIndex;

    public DragBubbleView(Context context) {
        super(context);
    }

    public DragBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DragBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragBubbleView);

        mBubbleRadius = array.getDimension(R.styleable.DragBubbleView_bubble_radius, mBubbleRadius);
        mBubbleColor = array.getColor(R.styleable.DragBubbleView_bubble_color, Color.RED);
        mTextStr = array.getString(R.styleable.DragBubbleView_bubble_text);
        mTextSize = array.getDimension(R.styleable.DragBubbleView_bubble_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.DragBubbleView_bubble_textColor, Color.WHITE);
        array.recycle();

        //文本画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);

        //        textSize
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        //抗锯齿  气泡画笔
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mTextRect = new Rect();
        mBubMoveableRadius = mBubbleRadius;
        mBubStillRadius = mBubbleRadius;

        //初始化贝塞尔曲线
        mBezierPath = new Path();
        mMaxDist = 8 * mBubbleRadius;
        mBurstRect = new Rect();
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            //将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }
    }

    //onSizeChanged   w  h   父容器宽高改变  mBubMoveableCenter  null  1  不等于 2
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //不动气泡中心
        if (mBubStillCenter == null) {
            mBubStillCenter = new PointF(w / 2, h / 2);
        } else {
            mBubStillCenter.set(w / 2, h / 2);
        }

        //动气泡中心
        if (mBubMoveableCenter == null) {
            mBubMoveableCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMoveableCenter.set(w / 2, h / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //一定要分状态
        if (mBubbleState != BUBBLE_STATE_DISMISS) {
            canvas.drawCircle(mBubMoveableCenter.x, mBubMoveableCenter.y, mBubbleRadius, mBubblePaint);
            mTextPaint.getTextBounds(mTextStr, 0, mTextStr.length(), mTextRect);
            canvas.drawText(mTextStr, mBubMoveableCenter.x - mTextRect.width() / 2,
                    mBubMoveableCenter.y + mTextRect.height() / 2, mTextPaint);
        }

        //动态状态
        if (mBubbleState == BUBBLE_STATE_CONNECT) {
            canvas.drawCircle(mBubStillCenter.x, mBubStillCenter.y, mBubStillRadius, mBubblePaint);

            //根据两个圆形圆心的距离计算sin和cos值
            float cosTheta = (mBubMoveableCenter.x - mBubStillCenter.x) / mDist;//正值

            float sinTheta = (mBubMoveableCenter.y - mBubStillCenter.y) / mDist;//负值

            //        A
            float iBubStillStartX = mBubStillCenter.x - mBubStillRadius * sinTheta;
            float iBubStillStartY = mBubStillCenter.y + mBubStillRadius * cosTheta;

// B
            float iBubMoveableEndX = mBubMoveableCenter.x - mBubMoveableRadius * sinTheta;
            float iBubMoveableEndY = mBubMoveableCenter.y + mBubMoveableRadius * cosTheta;
            //C
            float iBubMoveableStartX = mBubMoveableCenter.x +
                    mBubMoveableRadius * sinTheta;
            float iBubMoveableStartY = mBubMoveableCenter.y - mBubMoveableRadius * cosTheta;

            //D
            float iBubStillEndX = mBubStillCenter.x + mBubStillRadius * sinTheta;
            float iBubStillEndY = mBubStillCenter.y - mBubStillRadius * cosTheta;

            //  G计算控制点坐标，两个圆心的中点
            int iAnchorX = (int) ((mBubStillCenter.x + mBubMoveableCenter.x) / 2);
            int iAnchorY = (int) ((mBubStillCenter.y + mBubMoveableCenter.y) / 2);

            mBezierPath.reset();
            //            移动到B点
            // 画上半弧
            mBezierPath.moveTo(iBubStillStartX, iBubStillStartY);
            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubMoveableEndX, iBubMoveableEndY);
            // 画下半弧
            mBezierPath.lineTo(iBubMoveableStartX, iBubMoveableStartY);
            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubStillEndX, iBubStillEndY);
            mBezierPath.close();

            canvas.drawPath(mBezierPath, mBubblePaint);
//            起始点 终止点  控制点     一模一样  曲线绝对一样
//            1

        }

        //爆炸状态
        if (mBubbleState == BUBBLE_STATE_APART) {
//onDraw方法中

            mBurstRect.set((int) (mBubMoveableCenter.x - mBubMoveableRadius),
                    (int) (mBubMoveableCenter.y - mBubMoveableRadius),
                    (int) (mBubMoveableCenter.x + mBubMoveableRadius),
                    (int) (mBubMoveableCenter.y + mBubMoveableRadius));

            canvas.drawBitmap(mBurstBitmapsArray[mCurDrawableIndex], null,
                    mBurstRect, mBubblePaint);
        }

        mTextPaint.getTextBounds(mTextStr,
                0, mTextStr.length(), mTextRect);

        canvas.drawText(mTextStr, mBubMoveableCenter.x - mTextRect.width() / 2,
                mBubMoveableCenter.y + mTextRect.height() / 2, mTextPaint);
    }

    //思路     简单   构思   步骤
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDist = (float) Math.hypot(event.getX() - mBubStillCenter.x, event.getY() - mBubStillCenter.y);
                if (mDist < mBubbleRadius) {
                    mBubbleState = BUBBLE_STATE_CONNECT;
                } else {
                    mBubbleState = BUBBLE_STATE_DEFAUL;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.d("MN", "ACTION_MOVE");
                mBubMoveableCenter.x = event.getX();
                mBubMoveableCenter.y = event.getY();
                mDist = (float) Math.hypot(event.getX() - mBubStillCenter.x,
                        event.getY() - mBubStillCenter.y);
                if (mBubbleState == BUBBLE_STATE_CONNECT) {
                    if (mDist > mMaxDist) {
                        mBubbleState = BUBBLE_STATE_APART;
                    } else {
                        mBubStillRadius = mBubbleRadius - mDist / 8;
                    }

                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //两种情况，弹回去，爆炸
                if (mBubbleState == BUBBLE_STATE_CONNECT) {
//没有超出了最大距离
                    //弹回的动画
                    startBubbleRestAnim();
                } else if (mBubbleState == BUBBLE_STATE_APART) {
//超出了最大距离
                    if (mDist < 2 * mBubbleRadius) {
                        //弹回的动画
                        startBubbleRestAnim();
                    } else {
                        //炸裂的动画
                        startBubbleBurstAnim();
                    }
                }

                break;
            }

        }
        return true;
    }

    /**
     * 气泡回弹动画
     */
    private void startBubbleRestAnim() {
        ValueAnimator anim = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(mBubMoveableCenter.x, mBubMoveableCenter.y),
                new PointF(mBubStillCenter.x, mBubStillCenter.y));
        anim.setDuration(400);
//        反向执行  加速回来
        anim.setInterpolator(new OvershootInterpolator(5f));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBubMoveableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    /**
     * 气泡爆炸动画
     */
    private void startBubbleBurstAnim() {
        //气泡改为消失状态
        mBubbleState = BUBBLE_STATE_DISMISS;
        mIsBurstAnimStart = true;
        //做一个int型属性动画，从0~mBurstDrawablesArray.length结束
        ValueAnimator anim = ValueAnimator.ofInt(0, mBurstDrawablesArray.length);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置当前绘制的爆炸图片index
                mCurDrawableIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //修改动画执行标志
                mIsBurstAnimStart = false;
            }
        });
        anim.start();
    }
}
