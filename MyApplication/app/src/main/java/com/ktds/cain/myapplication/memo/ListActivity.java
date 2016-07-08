package com.ktds.cain.myapplication.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ktds.cain.myapplication.R;
import com.ktds.cain.myapplication.memo.db.DBHelper;
import com.ktds.cain.myapplication.memo.vo.Memo;
import com.ktds.cain.myapplication.utility.ArticleListAdapter;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView lvArticles;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // 초기 테이블 생성
        createTable();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "글쓰기", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);
                return false;
            }
        });


        List<Memo> memos = dbHelper.getAllArticles();
        if(memos == null ) {
            dbHelper = new DBHelper(ListActivity.this, "MEMO", null, DBHelper.DB_VERSION);
        }
        //2. ListView
        lvArticles.setAdapter(new ArticleListAdapter(memos, ListActivity.this));

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createTable() {
        if(dbHelper == null ) {
            dbHelper = new DBHelper(ListActivity.this, "MEMO", null, DBHelper.DB_VERSION);
        }
    }

}
