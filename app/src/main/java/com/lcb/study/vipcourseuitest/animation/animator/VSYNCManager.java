package com.lcb.study.vipcourseuitest.animation.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟VSYNC 信号
 */
public class VSYNCManager {
    private static final VSYNCManager ourInstance = new VSYNCManager();

    public static VSYNCManager getInstance() {
        return ourInstance;
    }

    private VSYNCManager() {
        new Thread(runnable).start();
    }
    public void add(AnimationFrameCallback animationFrameCallback) {
        list.add(animationFrameCallback);
    }
    public void remove(AnimationFrameCallback animationFrameCallback) {
        list.remove(animationFrameCallback);
    }
    //观察者集合
    private List<AnimationFrameCallback> list = new ArrayList<>();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {

                try {
                    //通过睡眠实现
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //通知所有观察者
                for (AnimationFrameCallback animationFrameCallback : list) {
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };
    interface AnimationFrameCallback {
        boolean doAnimationFrame(long currentTime);
    }
}
