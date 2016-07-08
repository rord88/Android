package com.ktds.cain.simpleboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ktds.cain.simpleboard.db.SimpleDB;
import com.ktds.cain.simpleboard.vo.ArticleVO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preparedSimpleDB();

        LinearLayout li = (LinearLayout) findViewById(R.id.itemList);

        // 반복 시작
        for( int i =0; i<SimpleDB.getIndexes().size(); i++) {
            Button button = new AppCompatButton(this);
            button.setText(SimpleDB.getIndexes().get(i));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button) v).getText();
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("key", buttonText);
                    startActivity(intent);
                }
            });

            li.addView(button);
        }

        //반복끝
    }

    private void preparedSimpleDB() {
        for (int i = 1; i<100; i++) {
            SimpleDB.addArticle(i + "article", new ArticleVO(i, "첫째글", "리스트", "누구"));
        }
    }

    //private long pressedTime;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        // this를 쓰나 getApplicationContext를 쓰나 같은의미.
        // 사용자에 직관적으로 목적을 제시하여 버튼형식으러
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog  .setTitle("finish")
                .setMessage("really?");
//                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "clicked yes", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "clicked cancel", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("no", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "clicked no", Toast.LENGTH_SHORT).show();
//                    }
//                })
        dialog.create();
        dialog.show();


        // Alert로 띄어서 종료.



        // 뒤로가기 두번 누르면 종료.
        /*if ( pressedTime == 0) {
            Toast.makeText(MainActivity.this, "한번 더 눌러야 종료", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if(seconds > 2000) {
                pressedTime = 0;

            } else {
                super.onBackPressed();
                //finish();
            }
        }*/
    }
}
