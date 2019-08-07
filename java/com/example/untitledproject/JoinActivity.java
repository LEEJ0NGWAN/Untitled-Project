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

public class JoinActivity extends AppCompatActivity{
    String email = null, pw = null, pw2 = null;
    ClearEditText emailPut, pwPut, pwPut2;
    Button joinButton;
    View.OnClickListener joinListener;
    View.OnKeyListener enterKeyListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setListeners();
        initialSetting();
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
    public void setListeners(){
        joinListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    email = emailPut.getText().toString();
                    pw = pwPut.getText().toString();
                    pw2 = pwPut2.getText().toString();
                    boolean chkEmail = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", email);
                    boolean chkPW = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", pw);
                    if(pw.length() < 8 || 20 < pw.length())
                        Toast.makeText(JoinActivity.this, "비밀번호는 8~20자리로 해주세요!", Toast.LENGTH_SHORT).show();
                    else if(!pw.equals(pw2))
                        Toast.makeText(JoinActivity.this, "비밀번호 재확인해주세요!", Toast.LENGTH_SHORT).show();
                    else if(email.length()<= 2)
                        Toast.makeText(JoinActivity.this, "메일주소가 너무 짧군요!", Toast.LENGTH_SHORT).show();
                    else if(pw.equals(pw2) && email.length() > 2 && chkEmail && chkPW){
                        email = email.replaceAll("\\\\","\\\\\\\\");
                        email = email.replaceAll("'", "\\\\'");
                        pw = pw.replaceAll("\\\\","\\\\\\\\");
                        pw = pw.replaceAll("'", "\\\\'");
                        NewJoin createUser = new NewJoin(email,pw,JoinActivity.this, JoinActivity.this);
                        createUser.execute();
                    }
                    else
                        Toast.makeText(JoinActivity.this, "영문자,숫자,특수기호만 입력가능합니다!", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("err",e.getMessage());
                    Toast.makeText(JoinActivity.this,"빈칸이 있군요!",Toast.LENGTH_SHORT).show();
                }
            }
        };
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
    }
    public void initialSetting() {
        emailPut = findViewById(R.id.emailPut);
        emailPut.setOnKeyListener(enterKeyListener);
        pwPut = findViewById(R.id.pwPut);
        pwPut.setOnKeyListener(enterKeyListener);
        pwPut2 = findViewById(R.id.pwPut2);
        pwPut2.setOnKeyListener(enterKeyListener);
        joinButton = findViewById(R.id.joinButton);
        joinButton.setOnClickListener(joinListener);
    }
    public void closeKeyboard() {
        if(getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailPut.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwPut.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwPut2.getWindowToken(), 0);
    }
}
