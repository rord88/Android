package com.ktds.cain.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ktds.cain.myapplication.calculator.CalculatorFragment;
import com.ktds.cain.myapplication.calculator.ResultFragment;
import com.ktds.cain.myapplication.memo.ListActivity;
import com.ktds.cain.myapplication.memo.db.DBHelper;
import com.ktds.cain.myapplication.pingTest.PingFragment;
import com.ktds.cain.myapplication.utility.FragmentReplaceable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable {

    private Fragment mainFragment;
    private Fragment calculatorFragment;
    private Fragment resultFragment;
    private Fragment pingFragment;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFragment = new MainFragment();
        calculatorFragment = new CalculatorFragment();
        resultFragment = new ResultFragment();
        pingFragment = new PingFragment();
        /**
         * 기본화면 설정
         */
        openFragment(mainFragment);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculator) {
            // Handle the camera action
            Toast.makeText(MainActivity.this, "계산기를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            openFragment(calculatorFragment);
        } else if (id == R.id.nav_pingTest) {
            Toast.makeText(MainActivity.this, "핑테스트를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            openFragment(pingFragment);
        } else if (id == R.id.nav_memo) {
            Toast.makeText(MainActivity.this, "메모장을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        if( fragment instanceof CalculatorFragment ) {
            calculatorFragment = fragment;
            openFragment(calculatorFragment);
        } else if ( fragment instanceof ResultFragment ) {
            resultFragment = fragment;
            openFragment(resultFragment);
        } else if ( fragment instanceof PingFragment ) {
            pingFragment = fragment;
            openFragment(pingFragment);
        }
    }

    @Override
    public void openFragment(final Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
