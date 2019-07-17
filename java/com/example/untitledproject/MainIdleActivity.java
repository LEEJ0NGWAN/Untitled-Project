package com.example.untitledproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;

public class MainIdleActivity extends AppCompatActivity {
    PopupMenu myPageMenu;
    FloatingActionButton myPageButton;
    Intent intentToUnivHier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainidle);
        myPageButton = findViewById(R.id.myPageButton);
        myPageMenu = new PopupMenu(this, myPageButton);
        this.getMenuInflater().inflate(R.menu.mypage_menu, myPageMenu.getMenu());
        intentToUnivHier = new Intent(this,UnivHierarchyActivity.class);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPageMenu.show();
            }
        });
        myPageMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.univHierMenu:
                        startActivity(intentToUnivHier);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
