package com.ktds.cain.mybeaconapplication.MyApplication;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.ktds.cain.mybeaconapplication.CouponActivity;
import com.ktds.cain.mybeaconapplication.SplashActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by MinChang Jang on 2016-06-23.
 */
public class MyApplication extends Application {

    // 비콘을 관리하는 클래스.
    private BeaconManager beaconManager;

    /**
     * Application을 설치할 때 실행됨.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        // Application 설치가 끝나면 Beacon Monitoring Service를 시작한다.
        // Application을 종료하더라도 Service가 계속 실행된다.
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                //비콘 수신대, 서비스이름, beaconId, Major, Minor 같은범위안에 없어도 같은 값이 떠도 문제없음.
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
                        0, 0));
            }
        });

        // Android 단말이 Beacon 의 송신 범위에 들어가거나, 나왔을 때를 체크한다.
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            //수신감도
            public void onEnteredRegion(Region region, List<Beacon> list) {
//                showNotification("들어옴", "비콘 연결됨" + list.get(0).getRssi());

                for (Beacon beacon : list) {
                    Log.d("Beacon", beacon.getProximityUUID()+"");
                    Log.d("Beacon", beacon.getMajor()+"");
                    Log.d("Beacon", beacon.getMacAddress()+"");
                    Log.d("Beacon", beacon.getMinor()+"");
                }

                // intent를 적용했을 때 아래 주석으로 처리후에 간단하게 처리가능

                // 동일 액티비티가 이미 실행되어 있는 상태라면, 비콘 신호가 들어오더라도 실행되지 않는다.
                if ( !isAlreadyRunActivity()) {
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("executeType", "beacon");

                    getApplicationContext().startActivity(intent);
                } else {
                    showNotification("쿠폰", "사용가능한 쿠폰이 있습니다.");
                }

//              getApplicationContext()
//                      .startActivity(new Intent(getApplicationContext(), SplashActivity.class)
//                              .putExtra("uuid", String.valueOf(list.get(0).getProximityUUID()) )
//                              .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            @Override
            public void onExitedRegion(Region region) {
                showNotification("나감", "비콘 연결끊김");
            }
        });
    }

    /**
     * Notification으로 Beacon 의 신호가 연결되거나 끊겼음을 알림.
     * @param title
     * @param message
     */
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, CouponActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                //Ticker를 사용하려면 setPriority를 적용시켜주어야 적용된다.
                .setTicker("[Coupon] 사용가능한 쿠폰이 있습니다.")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                //우선순위 조절로 Notification을 정의
                .setPriority(Notification.PRIORITY_HIGH)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private boolean isAlreadyRunActivity() {
        // 만약 어플리케이션이 실행이 되어있다면, 다시 실행할 필요 없이, 사용자에게 재실행 시킬지의 여부를 알려준다.
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(9999);

        String activityName = "";
        for(int i = 0; i < taskInfos.size(); i++) {
            activityName = taskInfos.get(i).topActivity.getPackageName();

            if(activityName.startsWith("com.ktds.cain.mybeaconapplication")) {
                return true;

            }
        }
        return false;
    }

}