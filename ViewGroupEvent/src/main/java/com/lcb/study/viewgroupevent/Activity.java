package com.lcb.study.viewgroupevent;

import com.lcb.study.viewgroupevent.listener.OnClickListener;
import com.lcb.study.viewgroupevent.listener.OnTouchListener;

/**
 * Created by ${lichangbin} on 2020/7/14.
 */
public class Activity {
    public static void main(String[] arg) {

        //初始化布局
        ViewGroup viewGroup = new ViewGroup(0, 0, 1080, 1920);
//        {
//            @Override
//            public boolean onInterceptTouchEvent(MotionEvent event) {
//                return false;
//            }
//        };
        viewGroup.setName("parent container ");
        viewGroup.setmOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("parent container onTouch ");
                return false;
            }
        });


        ViewGroup viewGroup1 = new ViewGroup(0, 0, 500, 500);
        viewGroup1.setName("child container");

        viewGroup1.setmOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("child container onTouch");
                return false;
            }
        });



        View view = new View(0, 0, 200, 200);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("view  accept onClick  ");
            }
        });
        viewGroup1.addView(view);
        viewGroup.addView(viewGroup1);

//        view.setmOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("view   OnTouch ");
//                return true;
//            }
//        });


        MotionEvent motionEvent = new MotionEvent(100, 100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

        //        顶级容器分发
        viewGroup.dispatchTouchEvent(motionEvent);
    }
}
