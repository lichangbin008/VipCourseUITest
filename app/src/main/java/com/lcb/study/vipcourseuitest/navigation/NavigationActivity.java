package com.lcb.study.vipcourseuitest.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lcb.study.vipcourseuitest.R;
import com.lcb.study.vipcourseuitest.navigation.bean.Destination;
import com.lcb.study.vipcourseuitest.navigation.fragment.EFragment;
import com.lcb.study.vipcourseuitest.navigation.util.AppConfig;
import com.lcb.study.vipcourseuitest.navigation.util.NavGraphBuilder;

import java.util.HashMap;
import java.util.Iterator;

public class NavigationActivity extends AppCompatActivity  {

    NavController navController;
    BottomNavigationView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        button=findViewById(R.id.button);
        //获取NavController
//        navController = Navigation.findNavController(this, R.id.nav_fragment);

        //NavigationUI类  是用来对AppBar和navController进行绑定
//        NavigationUI.setupWithNavController(button,navController);

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

        //以下是自定义
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        navController = NavHostFragment.findNavController(fragment);
        NavGraphBuilder.build(this, navController, fragment.getId());

        onPrepareOptionsMenu(button.getMenu());

        //注册点击事件
        button.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                navController.navigate(menuItem.getItemId());
                return !TextUtils.isEmpty(menuItem.getTitle());
            }
        });


    }

//    @Override
//    public void onFragmentInteraction(String name) {
//        Log.e("MN--------->",name);
//    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        Iterator<String> iterator = destConfig.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            Destination destination = destConfig.get(next);
            menu.add(0, destination.getId(), 0, "新建短信").setIcon(R.mipmap.cart);
        }
        return true;
    }
}
