package com.ktds.cain.customlistview.facebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ktds.cain.customlistview.R;
import com.restfb.types.Post;

public class DetailActivity extends ActionBarActivity {

    private TextView tvId;
    private TextView tvLikes;
    private TextView tvMessage;
    private TextView tvCreateTime;
    private TextView tvComments;

    private Facebook facebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        facebook = new Facebook(this);
        handler = new Handler();

        setTitle("상세 정보 보기");

        tvId = (TextView) findViewById(R.id.tvId);
        tvLikes = (TextView) findViewById(R.id.tvLikes);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvCreateTime = (TextView) findViewById(R.id.tvCreateTime);
        tvComments = (TextView) findViewById(R.id.tvComments);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String postId = (String) intent.getStringExtra("postId");
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                if (facebook.isLogin()) {
                    facebook.getDetail(postId, new Facebook.GetDetailInformation() {
                        @Override
                        public void details(final Post post) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvId.setText(post.getFrom().getName());
                                    if (post.getLikes() == null) {
                                        tvLikes.setText("Likes is 0");
                                        if(post.getComments() == null) {
                                            tvComments.setText("Comments Is Not Included.");
                                            if(post.getMessage() == null) {
                                                tvMessage.setText("Message Is Not Included.");
                                            } else {
                                                tvMessage.setText(post.getMessage());
                                            }
                                        } else {
                                            tvComments.setText(post.getComments() + "");
                                        }
                                    } else {
                                        tvLikes.setText(post.getLikes().getData().size() + "");
                                    }
                                    tvCreateTime.setText(post.getCreatedTime() + "");
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}