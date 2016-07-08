package com.ktds.cain.mybeaconapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private Region region;

    private TextView tvId;

    private boolean isConnected;

    /**
     * 주석 해제시, 비콘과 거리가 멀어질 수록 -값이 커지고, 가까워질수록 0에 가까워 진다.
     * getRssi - 신호강도
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvId);

        beaconManager = new BeaconManager(this);

        // add this below:
        //비콘 수신범위를 갱신
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Log.d("Airport", "Nearest places: " + nearestBeacon.getRssi());
                        // nearestBean.getRssi() : 비콘의 수신강도
                    tvId.setText(nearestBeacon.getRssi() + "");
                        if( !isConnected && nearestBeacon.getRssi() > -70 ) {
                            isConnected = true;
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("알림")
                                    .setMessage("비콘이 연결")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).create().show();
                        } else if ( nearestBeacon.getRssi() < -70 ){
                            Toast.makeText(MainActivity.this, "연결해제", Toast.LENGTH_SHORT).show();
                            isConnected = false;
                        }
                }
            }
        });

        region = new Region("ranged region",
                UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), null, null); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
    }

    @Override
    protected void onResume() {
        super.onResume();

        //블루투스 권한 승낙 및 블루투스 활성화 시키는 코드
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        // 어플리케이션 내부 이외에 더이상 모니터링 하지 않겠다는 것임.
        //beaconManager.stopRanging(region);

        super.onPause();
    }
}
