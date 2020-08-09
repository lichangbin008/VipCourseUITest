package com.lcb.study.vipcourseuitest.navigation.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lcb.courses.annotation.FragmentDestination;
import com.lcb.study.vipcourseuitest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * Created by ${lichangbin} on 2020/8/5.
 */
@FragmentDestination(pageUrl = "afragment",asStarter = true)
public class AFragment extends Fragment {
    Button button;
    private static final String CHANNEL_ID = "1";
    private static final int notificationId = 8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        String name=getArguments().getString("name");
//        Log.e("MN--->",name);
        Log.e("MN--->","onCreateView");
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendNotification();
//                Bundle bundle = new Bundle();
//				bundle.putString("name","from AFragment");
//				Navigation.findNavController(getView()).navigate(R.id.action_afragment_to_efragment);
            }
        });
    }

    /**
     * 通过PendingIntent设置，当通知被点击后需要跳转到哪个destination，以及传递的参数
     */
//    private PendingIntent getPendingIntent() {
//        if (getActivity() != null) {
//            Bundle bundle = new Bundle();
//            bundle.putString("params", "from Notification");
//            return Navigation
//                    .findNavController(getActivity(), R.id.button)
//                    .createDeepLink()
//                    .setGraph(R.navigation.nav_graph)
//                    //目的地Frgment
//                    .setDestination(R.id.bfragment)
//                    .setArguments(bundle)
//                    .createPendingIntent();
//        }
//        return null;
//    }


    /**
     * 向通知栏发送一个通知
     */
//    private void sendNotification() {
//        if (getActivity() == null) {
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "ChannelName", importance);
//            channel.setDescription("description");
//            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("MN_VIP_Navigation")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(getPendingIntent())
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//        notificationManager.notify(notificationId, builder.build());
//    }
}
