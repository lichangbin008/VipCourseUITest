package com.lcb.study.vipcourseuitest.navigation.util;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Created by ${lichangbin} on 2020/8/9.
 */
public class AppGlobals {
    private static Application sApplication;

    public static Application getApplication(){
        if(sApplication == null){
            try {
                Method currentApplication = Class.forName("android.app.ActivityThread").
                        getDeclaredMethod("currentApplication");
                sApplication = (Application) currentApplication.invoke(null, new Object[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
