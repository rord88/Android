package com.ktds.cain.mylandscapelayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "바뀜", Toast.LENGTH_SHORT).show();

        etText = (EditText) findViewById(R.id.etText);



        getWindowManager().getDefaultDisplay().getOrientation();
        int screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        int screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        if (screenWidth > screenHeight) {   }

    }

    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String text = etText.getText().toString();

        setContentView(R.layout.activity_main);
        etText = (EditText) findViewById(R.id.etText);
        etText.setText(text);


    }
}
