package com.ktds.cain.myrequestweb;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends ActionBarActivity {

    private Button btnSearch;
    private EditText etURL;
    private TextView tvResult;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        etURL = (EditText) findViewById(R.id.etURL);
        tvResult = (TextView) findViewById(R.id.tvResult);
        handler = new Handler();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HTTP Send to Request. Work Thread.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Task Start. Brief URL.
                        String url = etURL.getText().toString();
                        try {
                            // Answer Request URL Info
                            URL httpURL = new URL(url);
                            // ready to Answer before requesting.
                            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            // Maximum response Interval Time. if 5 second delay disconnection.
                            conn.setConnectTimeout(5000);

                            /**
                             *  send to GET Method for requesting.
                             *  if want to post, write post.
                             */
                            conn.setRequestMethod("GET");
                            // send to Request. Coincide response
                            // 요청을 보내고, 동시에 응답받기.
                            int responseCode = conn.getResponseCode();
                            // 요청과 응답이 제대로 이루어졌는지 검사한다.
                            // HttpURLConnection.HTTP_OK : 응답이 200 OK라는 의미
                            if( responseCode == HttpURLConnection.HTTP_OK) {
                                // 응답 본문 전체를 담는다.

                                final StringBuffer responseBody = new StringBuffer();
                                // 응답 본문의 한 출씩 얻어오기.
                                String line = null;

                                // 응답본문을 담고 있는 input Stream을 얻어오기.
                                // BufferedReader는 InputStream을 한줄씩 얻어 올 수 있는 객체.
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                                while ( (line = reader.readLine()) != null ) {
                                    // if line haven't sentences can't finish.

                                    //응답 본문 한줄씩 결과 객체에 담기.
                                    // 줄바꿈을 위해서 매 라인 끝마다 "\n"을 더해준다.

                                    responseBody.append(line + "\n");
                                }
                                // progressively, disconnect request to response.
                                reader.close();
                                conn.disconnect();

                                /**
                                 * 독립된 Thread에서 Android Application 의 Main Thread로 접근 할 수 있는 Handler를 만들어서 UI View를 컨트롤 한다.
                                 */
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Viewing Results for User.
                                        tvResult.setText(responseBody.toString());
                                    }
                                });
                            }

                        } catch (MalformedURLException e) {
                            return ;
                            //throw new RuntimeException(e.getMessage(), e);
                        } catch (IOException e) {
                            return;
                        }


                    }
                }).start();

            }
        });


    }
}

