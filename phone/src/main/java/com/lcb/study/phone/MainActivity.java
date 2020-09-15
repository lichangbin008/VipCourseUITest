package com.lcb.study.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.lcb.study.vipcourseuitest.IMyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.lcb.study.vipcourseuitest","com.lcb.study.vipcourseuitest.ManiuService"));
        bindService(intent,new ManiuServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    class ManiuServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务端的引用，客户端发送信息
            IMyService iRequest = IMyService.Stub.asInterface(service);
            try {
                Toast.makeText(MainActivity.this, "  " + iRequest.request("data"), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
