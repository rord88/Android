package com.ktds.cain.customlistview.facebook;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ktds.cain.customlistview.R;

public class WritePostActivity extends ActionBarActivity {

    private EditText etPost;
    private Facebook myFacebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        myFacebook = new Facebook(this);
        handler = new Handler();
        setTitle("New Article");
        ActionBar actionBar = getSupportActionBar();
        //버튼보여주기
        actionBar.setDisplayHomeAsUpEnabled(true);
        //버튼클릭시 적용 -(없어도 된다.)
       // actionBar.setHomeButtonEnabled(true);

        etPost = (EditText) findViewById(R.id.etPost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if( itemId == R.id.done) {
            //Validation Check
            if ( etPost.getText().toString().length() == 0) {
                //if not include contents, guide write.
                Toast.makeText(WritePostActivity.this, "please Write contents.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                // Facebook으로 post전송
                myFacebook.auth(new Facebook.After() {
                    @Override
                    public void doAfter(Context context) {
                        writePost();
                    }
                });
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void writePost() {
        myFacebook.publishing(etPost.getText().toString(), new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }
}
