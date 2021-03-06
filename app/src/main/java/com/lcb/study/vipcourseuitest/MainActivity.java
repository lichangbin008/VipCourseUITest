package com.lcb.study.vipcourseuitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lcb.study.vipcourseuitest.animation.AnimationActivity;
import com.lcb.study.vipcourseuitest.binder.BinderActivity;
import com.lcb.study.vipcourseuitest.bubble.BubbleActivity;
import com.lcb.study.vipcourseuitest.flowlayout.FlowLayoutActivity;
import com.lcb.study.vipcourseuitest.livedata.LiveDataActivity;
import com.lcb.study.vipcourseuitest.motionevent.EventDispatchActivity;
import com.lcb.study.vipcourseuitest.navigation.NavigationActivity;
import com.lcb.study.vipcourseuitest.recyclerview.RecyclerViewActivity;
import com.lcb.study.vipcourseuitest.viewmodel.DatabindingActivity;

public class MainActivity extends AppCompatActivity {

    private Button btFlow;

    private Button btRecyclerView;

    private Button btEvent;

    private Button btAnimation;

    private Button btLiveData;

    private Button btViewModel;

    private Button btNavigation;

    private Button btBubble;

    private Button btAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {
        btFlow = findViewById(R.id.bt_flow);
        btRecyclerView = findViewById(R.id.bt_recyclerview);
        btEvent = findViewById(R.id.bt_event);
        btAnimation = findViewById(R.id.bt_animation);
        btLiveData = findViewById(R.id.bt_livedata);
        btViewModel = findViewById(R.id.bt_viewmodel);
        btNavigation = findViewById(R.id.bt_navigation);
        btBubble = findViewById(R.id.bt_bubble);
        btAidl = findViewById(R.id.bt_aidl);
    }

    private void initListener() {
        btFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(intent);
            }
        });

        btRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        btEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventDispatchActivity.class);
                startActivity(intent);
            }
        });

        btAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent);
            }
        });

        btLiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiveDataActivity.class);
                startActivity(intent);
            }
        });

        btViewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabindingActivity.class);
                startActivity(intent);
            }
        });

        btNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        btBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BubbleActivity.class);
                startActivity(intent);
            }
        });

        btAidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BinderActivity.class);
                startActivity(intent);
            }
        });
    }
}
