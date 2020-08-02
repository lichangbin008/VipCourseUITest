package com.lcb.study.vipcourseuitest.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lcb.study.vipcourseuitest.R;

public class TwoActivity extends AppCompatActivity {
    LiveDataBus2.BusMutableLiveData<String> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        liveData = LiveDataBus2.getInstance().with("code", String.class);

        liveData.observe(TwoActivity.this,true, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("MN-----------1>", s);
            }
        });
    }

    public void postValue(View view) {
        liveData.postValue("我是TwoActivity传来的数据");
    }
}
