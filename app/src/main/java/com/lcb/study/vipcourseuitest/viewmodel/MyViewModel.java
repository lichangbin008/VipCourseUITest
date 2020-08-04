package com.lcb.study.vipcourseuitest.viewmodel;

import com.lcb.study.vipcourseuitest.viewmodel.model.User;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by ${lichangbin} on 2020/8/2.
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<String> number;
    private MutableLiveData<User> user;

    public MutableLiveData<String> getNumber() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue("");
        }
        return number;
    }

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }
}
