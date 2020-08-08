package com.lcb.study.vipcourseuitest.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lcb.study.vipcourseuitest.R;
import com.lcb.study.vipcourseuitest.navigation.fragment.EFragment;

public class NavigationActivity extends AppCompatActivity implements EFragment.OnFragmentInteractionListener {

    NavController navController;
    BottomNavigationView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        button=findViewById(R.id.button);
        //获取NavController
        navController = Navigation.findNavController(this, R.id.nav_fragment);

        //NavigationUI类  是用来对AppBar和navController进行绑定
        NavigationUI.setupWithNavController(button,navController);

        //manifest中已经绑定
//        button.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.bfragment:
//                        navController.navigate(
//                                R.id.to_bfragment);
//                        break;
//                    case R.id.cfragment:
//                        navController.navigate(
//                                R.id.to_cfragment);
//                        break;
//                    case R.id.dfragment:
//                        navController.navigate(
//                                R.id.to_dfragment);
//                        break;
//                    case R.id.efragment:
//                        navController.navigate(
//                                R.id.to_efragment);
//                        break;
//                    case R.id.afragment:
//                        navController.navigate(
//                                R.id.to_afragment);
//                        break;
//                }
//
//                return true;
//            }
//        });
    }

    @Override
    public void onFragmentInteraction(String name) {
        Log.e("MN--------->",name);
    }
}
