package com.lcb.study.vipcourseuitest.viewmodel.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * //单向绑定的第一种方法
 */
public class User extends BaseObservable {
    String name;

    String age;

    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }


    @Bindable
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
}
