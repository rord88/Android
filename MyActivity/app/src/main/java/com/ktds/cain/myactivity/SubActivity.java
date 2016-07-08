package com.ktds.cain.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //main에서 intent로 보냈기 때문에 받아올 때도 intent로 받아올 수 있다.
        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));
    }
}
