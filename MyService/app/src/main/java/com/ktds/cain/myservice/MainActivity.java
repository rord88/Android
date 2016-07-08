package com.ktds.cain.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ktds.cain.myservice.AIDL.IMyCounterService;
import com.ktds.cain.myservice.service.MyCounterService;

public class MainActivity extends AppCompatActivity {

    private TextView tvCounter;
    private Button btnPlay;
    private Button btnStop;
    private Intent intent;
    private boolean isRun = true;

    private IMyCounterService binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyCounterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = (TextView) findViewById(R.id.tvCounter);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MyCounterService.class);
//                startService(intent);
                bindService(intent, connection, BIND_AUTO_CREATE);
                isRun = true;
                new Thread(new GetCountThread()).start();


            }
        });
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MyCounterService.class);
                unbindService(connection);
                isRun = false;
                //stopService(intent);
            }
        });
    }

    private class GetCountThread implements Runnable {

        private Handler handler = new Handler();


        public void setRun(boolean run) {
            isRun = run;
        }

        @Override
        public void run() {

            while ( isRun ) {

                if(binder == null) {
                    continue;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tvCounter.setText(binder.getCount() + "");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
