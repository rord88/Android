package com.ktds.cain.mypracticeone;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class practice extends AppCompatActivity {

    private Button btnCall;
    private Button btnBrowser;
    private Button btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        btnCall = (Button) findViewById(R.id.btnCall);
        btnBrowser = (Button) findViewById(R.id.btnBrowser);
        btnContact = (Button) findViewById(R.id.btnContact);

        btnCall.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-7336-6004"));
                startActivity(intent);
            }
        });

        btnBrowser.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "json");
                startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intent);
            }
        });

    }


}
