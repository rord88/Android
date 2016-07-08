package com.ktds.cain.simpleboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.cain.simpleboard.db.SimpleDB;
import com.ktds.cain.simpleboard.vo.ArticleVO;

public class DetailActivity extends AppCompatActivity {

    //정석대로 작성방식

    private TextView tvSubject;
    private TextView tvArticleNumber;
    private TextView tvAuthor;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvArticleNumber = (TextView) findViewById(R.id.tvArticleNumber);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        ArticleVO articleVO = SimpleDB.getArticle(key);

        // 타이틀관련해서 작성
        setTitle(articleVO.getSubject());

        tvSubject.setText(articleVO.getSubject());
        tvArticleNumber.setText(articleVO.getArticleNo() + "");
        tvAuthor.setText(articleVO.getAuthor());
        tvDescription.setText(articleVO.getDescription());
    }

        //뒤로가기

        public void onBackPressed() {
            super.onBackPressed();
            Toast.makeText(DetailActivity.this, "Activity is finish", Toast.LENGTH_SHORT).show();
        }





}
