package com.ktds.cain.mypracticetwo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.cain.mypracticetwo.phoneBookDB.PhoneDB;
import com.ktds.cain.mypracticetwo.phoneBookVO.PhoneVO;

public class DetailPractice extends AppCompatActivity {

    private TextView tvName;
    private TextView tvLocation;
    private TextView tvPhoneNumber;
    private Button btnCall;
    private Button btnSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_practice);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        final PhoneVO phoneVO = PhoneDB.getPhoneInfo(key);

        setTitle(phoneVO.getName());
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(phoneVO.getName());
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocation.setText(phoneVO.getLocation());
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvPhoneNumber.setText(phoneVO.getPhoneNumber());

        btnCall = (Button) findViewById(R.id.btnCall);
        btnSMS = (Button) findViewById(R.id.btnSMS);

        btnCall.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneVO.getPhoneNumber()));
                startActivity(intent);
            }
        });

        btnSMS.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra("sms_body", "the sms text");
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(DetailPractice.this, "return to List", Toast.LENGTH_LONG).show();
    }
}