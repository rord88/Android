package com.ktds.cain.myservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.ktds.cain.myservice.AIDL.IMyCounterService;

import java.util.Locale;


public class MyCounterService extends Service {
    private  int count;
    private boolean isStop;
    private TextToSpeech tts;

    public MyCounterService() {
    }

    /**
     * Service와 Activity가 통신하기 위한 바인더 객체
     * Activity에게 getCount() 메소드를 제공해 SErvice의 Count 값을 전달.
     */
    IMyCounterService.Stub binder = new IMyCounterService.Stub() {
        @Override
        public int getCount() throws RemoteException {
            return count;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });


        Thread counter = new Thread(new Counter());
        counter.start();
    }

    /**
     * stopService 실행시 호출
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }

    public class Counter implements Runnable{
        private Handler handler = new Handler();

        @Override
        public void run() {

            for (count = 0; count < 50; count++) {
                if(isStop) {
                    break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyCounterService.this, count + "", Toast.LENGTH_SHORT).show();
                        Log.d("Count", count +"");
                    }
                });

                try{
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.getMessage();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "서비스 종료", Toast.LENGTH_SHORT).show();
                    tts.speak("완료되었습니다..", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }
}
