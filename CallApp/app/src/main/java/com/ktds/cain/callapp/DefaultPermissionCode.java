package com.ktds.cain.callapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 206-013 on 2016-06-13.
 */
public class DefaultPermissionCode extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  단말기의 Android OS 버전이 MashMellow 이상일때
         *  권한 체크 방법을 다르게 적용함
         *  Build.VERSION.SDK_INT : 단말기의 Android OS 버전을 가져옴.
         *  Build.VERSION.CODES.N : 버전별 닉네임을 가짐.
         */
        if ( Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            /**
             * 해당 APP이 특정 권한을 가지고 있는지 검사함.
             * 리턴결과는 PackageManager.PERMISSION_DENIED와 PackageManager.PERMISSION_GRANTED로 나눠짐.
             * PackageManager.PERMISSION_DENIED : 권한이 없음.
             * PackageManager.PERMISSION_GRANTED : 권한이 있음.
             */
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

            /**
             * 해당 권한이 없을 경우 처리 방법
             */
        if ( permissionCheck == PackageManager.PERMISSION_DENIED) {
            /**
             * 권한을 취득할 때 사용자로부터 확인을 받아야 하는지 확인
             * 여기서 False가 나올 경우는 해당 앱에서 한번이라도 권한을 Deny한 경우일 때 말고는 없음.
             * 권한에 대해서 허가하지 않은 경우 다시 한번 권한의 취득을 위해 사용자에게 이유를 고지해야 함.
             * Mashmellow 버전 이상부터 사용가능함.
             */
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                /**
                 * 권한 취득해야 하는 이유를 Dialog 등을 통해서 알린다.
                 */
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("권한 요청")
                        .setMessage("이 기능은 다음 권한을 필요로 합니다.")
                        .setPositiveButton("허가함", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * 권한의 취득을 요청한다.
                                 * 취득하고자 하는 권한을 배열에 넣고 요청한다.
                                 * 뒤에 들어가는 파라미터(1000)는 onRequestPermissionResult() 에서 권한 취득 결과에서 사용된다.
                                 * startActivityForResult의 Request Code와 유사함.
                                 */
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    DefaultPermissionCode.this.requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
                                }
                            }
                        })
                        .setNegativeButton("허가하지 않음", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * 권한 허가를 하지 않았을 경우
                                 * 대안요소를 찾거나 기능의 수행을 중지한다.
                                 */

                            }
                        }).create().show();

            } else {
                /**
                 * 권한의 취득 요청을 처음 할 때
                 * 권한의 취득을 요청한다.
                 * 취득하고자 하는 권한을 배열에 넣고 요청한다.
                 * 뒤에 들어가는 파라미터(1000)은 onRequestPermissionResult()에서 권한 취득 결과에서 사용된다.
                 * startActivityForResult의 Request Code와 유사함.
                 */
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
            }

        } else {
            /**
             * 이미 권한을 가지고 있을 경우
             * 해야할 일을 수행한다.
             */
            Log.d("PERMISSION", "YES");
        }

        }

    }
    /**
     * 신규로 권한을 요청해 그 응답이 돌아왔을 경우 실행됨.
     * @param requestCode : 권한 요청시 전송했던 코드.
     * @param permissions : 요청한 권한
     * @param grantResults : 해당 권한에 대한 결과
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if ( requestCode == 1000 ) {
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                /**
                 * 권한 획득완료
                 * 해야 할 일을 수행한다.
                 */
            Log.d("수행완료", "수행완료");
            } else {
                /**
                 * 권한 획득 실패
                 * 대안을 찾거나 기능의 수행을 중지.
                 */
            Log.d("수행중지", "수행중지");
            }
        }
    }
}
