package com.ktds.cain.mypracticetwo;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ktds.cain.mypracticetwo.phoneBookDB.PhoneDB;
import com.ktds.cain.mypracticetwo.phoneBookVO.PhoneVO;

public class Practice extends AppCompatActivity {

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        preparedPhoneInfoDB();

        LinearLayout li = (LinearLayout) findViewById(R.id.infoList);
        for (int i = 0; i < PhoneDB.getIndexes().size(); i++) {
            final Button button = new AppCompatButton(this);
            button.setText(PhoneDB.getIndexes().get(i));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button) v).getText();
                    Intent intent = new Intent(v.getContext(), DetailPractice.class);

                    intent.putExtra("key", buttonText);
                    startActivity(intent);
                }
            });
            li.addView(button);
        }

    }

    private void preparedPhoneInfoDB() {
        for (int i=1; i<100; i++) {
            PhoneDB.addPhoneInfo(i+"person", new PhoneVO(i, i+"name", i+"location", "tel:010-7777-00"+i));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (pressedTime == 0) {
            Toast.makeText(Practice.this, "종료를 원하시면 한번더 눌러주세요.", Toast.LENGTH_LONG).show();
            pressedTime = SystemClock.currentThreadTimeMillis();
        } else {
            int seconds = (int) (SystemClock.currentThreadTimeMillis() - pressedTime);
            if (seconds >= 2000) {
                pressedTime = 0;
            } else {
                super.onBackPressed();
                finish();
            }
        }
    }
}


