package com.ktds.sems.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by 206-013 on 2016-07-04.
 */
public class LoginTask extends AsyncTask<String, String, String> {

    private Activity activity;

    @Override
    protected String doInBackground(String... params) {

        String id = params[0];
        String pwd = params[1];

        HttpClient.Builder client = new HttpClient.Builder("POST", "http://10.225.152.172:8080/m/login");
        client.addOrReplaceParameter("id", id);
        client.addOrReplaceParameter("password", pwd);

        HttpClient post = client.create();
        post.request();


        return post.getBody();
    }

    @Override
    protected void onPostExecute(String s) {

        Gson gson = new Gson();
        // JSON 을 Class로 변환한다.
        LoginResult loginResult = gson.fromJson(s, LoginResult.class);

        if( loginResult.getResult().equals("true")) {
            HttpClient.SESSION_ID = loginResult.getSessionId();
            // 1. Login 이후의 액티비티를 생성해서 실행한다.

            // 2. 현재 MainActivity는 종료 시킴.

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity.getApplicationContext());
            dialog.setTitle("알림")
                    .setMessage("어플리케이션을 사용 할 수 없습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();



                        }
                    }).create().show();
        }

        Log.d("RESULT", s);

    }
}
