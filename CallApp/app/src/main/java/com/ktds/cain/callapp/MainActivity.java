package com.ktds.cain.callapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 */

public class MainActivity extends AppCompatActivity {
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // OS Validation Check( NOW : MashMallow )
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    // User Machine permission has "call" Validation Check.
//
//                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
//                    // why not uses boolean type validation check. because Android is C based code. C don't have boolean type.
//
//                    /**
//                     * Package is Application salts in Android. ( Android Application Package Equals ID )
//                     */
//                    // CALL_PHONE Permission haven't function.
//                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
//                        // user has denied this function history.
//                        // if have return true or not return false.
//                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
//                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                            dialogBuilder.setTitle("Required Permission.")
//                                    .setMessage("if you want to use this function, you must permit this Function \"CALL_PHONE\".")
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                // if you use this sentences, requestPermissions need to know what can be work if not this sentence make error.
//                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
//                                            }
//                                        }
//                                    })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Toast.makeText(MainActivity.this, "Function Cancle", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).create().show();
//                        } else {
//                            // firstly, request permission this function.
//                            // Request to Permission code 1000 for Android OS.
//                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
//                        }
//                    } else {
//                        // CALL_PHONE Permission have function.
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-7336-6004"));
//                        startActivity(intent);
//                    }
//
//
//                } else {
//                    // User Machine OS Version is Lower than MashMallow.
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-7336-6004"));
//                    startActivity(intent);
//                }
                int result = new PermissionRequester.Builder(MainActivity.this)
                        // brief default setting this sentences.
//                        .setTitle("권한 요청")
//                        .setMessage("권한을 요청합니다.")
//                        .setPositiveButtonName("네")
//                        .setNegativeButtonName("아니요.")
                        .create()
                        .request(Manifest.permission.CALL_PHONE, 1000 , new PermissionRequester.OnClickDenyButtonListener() {
                            @Override
                            public void onClick(Activity activity) {
                                Log.d("RESULT", "취소함.");
                            }
                        });

                if (result == PermissionRequester.ALREADY_GRANTED) {
                    Log.d("RESULT", "권한이 이미 존재함.");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
                        startActivity(intent);
                    }
                }
                else if(result == PermissionRequester.NOT_SUPPORT_VERSION)
                    Log.d("RESULT", "마쉬멜로우 이상 버젼 아님.");
                else if(result == PermissionRequester.REQUEST_PERMISSION)
                    Log.d("RESULT", "요청함. 응답을 기다림.");




            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults); <- don't need now.
        /**
         *  @param - requestCode : before uses code 1000
         *          - permissions : getPermissionCode
         *          - grantResults : validation after check grantResult
         *  @Result - Total Validation Checking
         */

        if (requestCode == 1000) {
            // if user permit this function
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-7336-6004"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "permission denied.", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
