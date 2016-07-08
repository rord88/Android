package com.ktds.cain.mydialog;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnDailog;
    private Button btnDailog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDailog = (Button) findViewById(R.id.btnDialog);
        btnDailog2= (Button) findViewById(R.id.btnDialog2);

        final List<String> selectItems = new ArrayList<String>();

        btnDailog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"computer", "game", "sports", "music", "movie"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("관심분야")
                        .setMultiChoiceItems(items, new boolean[]{true, true, true, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked) {
                                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                                    selectItems.add(items[which]);
                                } else {
                                    selectItems.remove(items[which]);
                                }
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectItems.size() == 0) {
                                    Toast.makeText(MainActivity.this, "선택을 해주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String items = "";
                                    for (String selectedItem : selectItems) {
                                        items += (selectedItem + ", ");
                                    }
                                    selectItems.clear();

                                    items = items.substring(0, items.length()-2);
                                    Toast.makeText(MainActivity.this, items, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).create().show();



            }
        });

        btnDailog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"computer", "game", "sports", "music", "movie"};
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("관심분야")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, items[selectedIndex[0]], Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

            }
        });




    }
}
