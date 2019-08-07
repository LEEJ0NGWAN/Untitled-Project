package com.example.untitledproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int stage = 0;
    String email, name;
    Toolbar mainToolbar;
    DrawerLayout drawerLayout;
    TextView nameView, emailView;
    ImageButton logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSetting();
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else{
            stage--;
            switch (stage) {
                case -2:
                    finish();
                case -1:
                    Toast.makeText(this, "한번 더 누르면 로그인 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                    FinishTimer finishTimer = new FinishTimer(this);
                    finishTimer.start();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_action, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_action :
                return true;
            case R.id.account_action :
                openMyPage();
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
    public void initialSetting() {
        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        name = intent.getExtras().getString("name");
        emailView = findViewById(R.id.emailView);
        nameView = findViewById(R.id.nameView);
        logoutButton = findViewById(R.id.logoutButton);
        drawerLayout = findViewById(R.id.mainDrawerLayout);
        mainToolbar = findViewById(R.id.mainToolbar) ;
        mainToolbar.setTitle("");
        nameView.setText(name);
        emailView.setText(email);
        setSupportActionBar(mainToolbar); //툴바를 이 액티비티의 액션바로 등록

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutDialog = new AlertDialog.Builder(MainActivity.this);
                logoutDialog
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 할까요?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
            }
        });
    }
    public void openMyPage() {
        drawerLayout.openDrawer(Gravity.START);
    }
}
