package com.lcb.study.vipcourseuitest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by ${lichangbin} on 2020/9/15.
 */
public class ManiuService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new IMyService.Stub() {
            @Override
            public String request(String data) throws RemoteException {
                return "码牛";
            }
        };
    }
}
