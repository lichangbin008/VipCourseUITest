package com.lcb.study.vipcourseuitest.livedata;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by ${lichangbin} on 2020/7/30.
 */
@SuppressLint("RestrictedApi")
public class MyLifeCycle implements GenericLifecycleObserver {
    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Log.e("MN----------->",event+"");
    }
}
