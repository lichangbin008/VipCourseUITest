package com.lcb.study.vipcourseuitest.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lcb.study.vipcourseuitest.R;
import com.lcb.study.vipcourseuitest.databinding.ActivityDatabindingBinding;
import com.lcb.study.vipcourseuitest.viewmodel.model.User;
import com.lcb.study.vipcourseuitest.viewmodel.model.User_2;

public class DatabindingActivity extends AppCompatActivity {

    ActivityDatabindingBinding viewDataBinding;
    User user;
    User_2 user_2;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_databinding);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
//        init_1();
//        init_2();
//        init_3();
        init_4();
    }

    private void init_4() {
        myViewModel=new MyViewModel();
        myViewModel.getNumber().setValue("40");
        User user =new User("张飞","99");
        myViewModel.getUser().setValue(user);
        viewDataBinding.setViewModel(myViewModel);
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MN----->",myViewModel.getUser().getValue().getName());
            }
        });
    }

    private void init_3() {
        final ObservableField<String> name = new ObservableField<>();
        final ObservableField<String> age = new ObservableField<>();
        name.set("张三");
        age.set("29");
        viewDataBinding.setName(name);
        viewDataBinding.setAge(age);
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.set("王五");
                age.set("34");
            }
        });
    }

    private void init_2() {
        user_2=new User_2();
        user_2.name.set("张三");
        user_2.age.set("29");
        viewDataBinding.setUser2(user_2);
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_2.name.set("王五");
                user_2.age.set("34");
            }
        });
    }

    private void init_1() {
        user =new User("张三","22");
        viewDataBinding.setUser(user);

        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName("王五");
                user.setAge("40");
            }
        });
    }
}
