package com.lcb.study.vipcourseuitest.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lcb.study.vipcourseuitest.R;

public class LiveDataActivity extends AppCompatActivity {

    //    MutableLiveData<String> liveData;
    LiveDataBus2.BusMutableLiveData<String> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        liveData=LiveDataBus2.getInstance().with("code", String.class);
//        liveData = new MutableLiveData<>();

        //可以在任意线程执行
        liveData.postValue("11111");
//        LiveDataActivity.this.getLifecycle().addObserver(new MyLifeCycle());


    }

    public void setValue(View view) {
        startActivity(new Intent(LiveDataActivity.this,TwoActivity.class));
//        liveData.observe(LiveDataActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Log.e("MN----------->",s);
//            }
//        });
    }
}
