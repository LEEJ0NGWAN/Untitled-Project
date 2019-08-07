package com.example.untitledproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity {
    String email, code;
    ClearEditText emailPut, codePut;
    Button sendCodeButton, sendPWButton;
    View.OnKeyListener enterKeyListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initialSetting();
        setListeners();
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();
        return true;
    }
    public void initialSetting() {
        emailPut = findViewById(R.id.emailPut_forgotAct);
        codePut = findViewById(R.id.codePut);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        sendPWButton = findViewById(R.id.sendPWButton);
    }
    public void setListeners() {
        enterKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        };
        emailPut.setOnKeyListener(enterKeyListener);
        codePut.setOnKeyListener(enterKeyListener);
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    closeKeyboard();
                    email = emailPut.getText().toString();
                    boolean chkEmail = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", email);
                    if(email.length() <= 2)
                        Toast.makeText(ForgotActivity.this, "메일 주소가 너무 짧군요!", Toast.LENGTH_SHORT).show();
                    else if(email.length() > 2 && chkEmail){
                        email = email.replaceAll("\\\\","\\\\\\\\");
                        email = email.replaceAll("'", "\\\\'");
                        SendCodeForNewPassword sendCodeForNewPassword = new SendCodeForNewPassword(email,ForgotActivity.this,ForgotActivity.this);
                        sendCodeForNewPassword.execute();
                    }
                    else
                        Toast.makeText(ForgotActivity.this, "영문자,숫자,특수기호만 입력해주세요!", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("err",e.getMessage());
                }
            }
        });
        sendPWButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    closeKeyboard();
                    code = codePut.getText().toString();
                    if(code.length() == 8){
                        SendNewPW sendNewPW = new SendNewPW(email,code,ForgotActivity.this,ForgotActivity.this);
                        sendNewPW.execute();
                    }
                    else
                        Toast.makeText(ForgotActivity.this, "8자리 숫자여야 합니다:D", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("err",e.getMessage());
                }
            }
        });
    }
    public void closeKeyboard() {
        if(getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailPut.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(codePut.getWindowToken(), 0);
    }
}
