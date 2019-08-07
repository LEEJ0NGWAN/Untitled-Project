package com.example.untitledproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity{
    int stage = 0;
    ClearEditText emailText, pwText;
    String email, pw;
    TextView titleText;
    Button turnOnLoginButton, loginButton, newButton, forgotButton;
    Intent intentToMainActivity, intentToJoinActivity, intentToForgotActivity;
    Animation fadeIn, fadeOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialSetting();
        setListeners();
    }
    @Override
    public void onBackPressed() {
        stage--;
        switch (stage) {
            case -2:
                finish();
            case -1:
                Toast.makeText(this, "한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
                FinishTimer finishTimer = new FinishTimer(this);
                finishTimer.start();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();
        return true;
    }
    public void initialSetting() {
        intentToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        intentToJoinActivity = new Intent(LoginActivity.this, JoinActivity.class);
        intentToForgotActivity = new Intent(LoginActivity.this, ForgotActivity.class);
        titleText = findViewById(R.id.titleText);
        emailText = findViewById(R.id.emailText);
        pwText = findViewById(R.id.pwText);
        turnOnLoginButton = findViewById(R.id.turnOnLoginButton);
        loginButton = findViewById(R.id.loginButton);
        newButton = findViewById(R.id.newButton);
        forgotButton = findViewById(R.id.forgotButton);
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);
        fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        titleText.setVisibility(View.VISIBLE);
        turnOnLoginButton.setVisibility(View.VISIBLE);
        forgotButton.setVisibility(View.VISIBLE);
        newButton.setVisibility(View.VISIBLE);
        turnOnLoginButton.startAnimation(fadeIn);
        forgotButton.startAnimation(fadeIn);
        newButton.startAnimation(fadeIn);
        titleText.startAnimation(fadeIn);
    }
    public void setListeners() {
        pwText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    closeKeyboard();
                    email = emailText.getText().toString();
                    pw = pwText.getText().toString();
                    boolean chkEmail = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", email);
                    boolean chkPW = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", pw);
                    if(email.length()<= 2)
                        Toast.makeText(LoginActivity.this, "메일주소가 너무 짧군요!", Toast.LENGTH_SHORT).show();
                    else if(email.length() > 2 && chkEmail && chkPW){
                        email = email.replaceAll("\\\\","\\\\\\\\");
                        email = email.replaceAll("'", "\\\\'");
                        pw = pw.replaceAll("\\\\","\\\\\\\\");
                        pw = pw.replaceAll("'", "\\\\'");
                        Login login = new Login(email, pw, LoginActivity.this, LoginActivity.this);
                        login.execute();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "영문자,숫자,특수기호만 입력가능합니다!", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("err",e.getMessage());
                    Toast.makeText(LoginActivity.this,"빈칸이 있군요!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        turnOnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText.setVisibility(View.VISIBLE);
                pwText.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                titleText.clearAnimation();
                newButton.clearAnimation();
                forgotButton.clearAnimation();
                turnOnLoginButton.clearAnimation();
                turnOnLoginButton.startAnimation(fadeOut);
                turnOnLoginButton.setVisibility(View.INVISIBLE);
                emailText.startAnimation(fadeIn);
                pwText.startAnimation(fadeIn);
                loginButton.startAnimation(fadeIn);
            }
        });
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToJoinActivity);
            }
        });
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToForgotActivity);
            }
        });
    }

    public void closeKeyboard() {
        stage = 0;
        if (getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwText.getWindowToken(), 0);
    }
}
