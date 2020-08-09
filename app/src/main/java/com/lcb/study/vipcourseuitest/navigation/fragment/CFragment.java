package com.lcb.study.vipcourseuitest.navigation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcb.courses.annotation.FragmentDestination;
import com.lcb.study.vipcourseuitest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by ${lichangbin} on 2020/8/5.
 */
@FragmentDestination(pageUrl = "cfragment",asStarter = false)
public class CFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        String name=getArguments().getString("name");
//        Log.e("MN--->",name);
        Log.e("MN--->","onCreateView");
        return inflater.inflate(R.layout.fragment_c,container,false);
    }
}
