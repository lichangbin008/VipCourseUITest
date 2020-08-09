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


@FragmentDestination(pageUrl = "bfragment",asStarter = false)
public class BFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        String params=getArguments().getString("params");
//        Log.e("MN--->",params);
        Log.e("MN--->","onCreateView");
        return inflater.inflate(R.layout.fragment_b,container,false);
    }
}
