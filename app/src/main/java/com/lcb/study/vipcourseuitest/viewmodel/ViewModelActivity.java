package com.lcb.study.vipcourseuitest.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lcb.study.vipcourseuitest.R;

/**
 * 一个activity度对应一个ViewModel
 */
public class ViewModelActivity extends AppCompatActivity {

    private MyViewModel myViewModel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);

        textView = findViewById(R.id.tv_num);
        //当我们执行这句代码的时候  会去ViewModelStore获取MyViewModel对象   如果没有 就创建一个
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        myViewModel.number = 111;
//        textView.setText(myViewModel.getNumber().getValue() + "");
//        myViewModel.getNumber().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                textView.setText(myViewModel.getNumber().getValue() + "");
//            }
//        });
    }

    public void changedata(View view) {
        myViewModel.getNumber().setValue(myViewModel.getNumber().getValue()+1);
//        textView.setText(myViewModel.getNumber().getValue() + "");
    }
}
