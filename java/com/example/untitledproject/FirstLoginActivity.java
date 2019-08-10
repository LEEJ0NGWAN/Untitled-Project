package com.example.untitledproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstLoginActivity extends AppCompatActivity {
    short stage = 0;
    TextView changingText;
    RecyclerView interestRecycler;
    InterestsAdapter interestsAdapter;
    Spinner majorTypeSpinner;
    ArrayAdapter majorTypeAdapter;
    ClearEditText changingEditText;
    Button firstLoginButton;
    String email;
    String name = null, majorType = "0", major = null, interests = null;
    Animation fadeIn;
    boolean[] interestsFlag = new boolean[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);
        initialSetting();
    }
    @Override
    public void onBackPressed() {
        stage--;
        switch (stage) {
            case -2:
                finish();
            case -1:
                Toast.makeText(this, "한번 더 누르면 로그인화면으로 이동합니다", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                welcomeStage();
                break;
            case 1:
                nameStage();
                break;
            case 2:
                majorTypeStage();
                break;
            case 3:
                majorStage();
                break;
        }
        //super.onBackPressed();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();
        if(stage <= 0){
            stage = 1;
            nameStage();
        }
        return true;
    }
    public void initialSetting() {
        Intent intentFromLoginActivity = getIntent();
        email = intentFromLoginActivity.getExtras().getString("email");
        majorTypeAdapter = ArrayAdapter.createFromResource(this, R.array.majorType, R.layout.major_type_item_layout);
        majorTypeSpinner = findViewById(R.id.majorTypeSpinner);
        majorTypeSpinner.setAdapter(majorTypeAdapter);
        changingText = findViewById(R.id.changingText);
        changingEditText = findViewById(R.id.changingEditText);
        firstLoginButton = findViewById(R.id.firstLoginButton);
        interestRecycler = findViewById(R.id.interestsRecycler);
        interestsAdapter = new InterestsAdapter(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.interests))), this);
        interestRecycler.setAdapter(interestsAdapter);
        interestRecycler.setLayoutManager(new LinearLayoutManager(this));
        Arrays.fill(interestsFlag,false);
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);
        changingText.startAnimation(fadeIn);
        firstLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    closeKeyboard();
                    String tempStr = changingEditText.getText().toString();
                    tempStr = tempStr.replaceAll("\\\\","\\\\\\\\");
                    tempStr = tempStr.replaceAll("'", "\\\\'");
                    switch (stage) {
                        case 1: //name
                            name = tempStr;
                            if(name.length() < 2)
                                Toast.makeText(FirstLoginActivity.this, "너무 짧군요!", Toast.LENGTH_SHORT).show();
                            else if(name.length() > 10)
                                Toast.makeText(FirstLoginActivity.this, "너무 길군요!", Toast.LENGTH_SHORT).show();
                            else{
                                NameCheck nameCheck = new NameCheck(name, FirstLoginActivity.this, FirstLoginActivity.this);
                                nameCheck.execute();
                            }
                            break;
                        case 2: //majorType
                            stage++;
                            changingEditText.setText(null);
                            majorStage();
                            break;
                        case 3: //major
                            major = tempStr;
                            if(major.length() < 2)
                                Toast.makeText(FirstLoginActivity.this, "너무 짧군요!", Toast.LENGTH_SHORT).show();
                            else if(major.length() > 20)
                                Toast.makeText(FirstLoginActivity.this, "너무 길군요!", Toast.LENGTH_SHORT).show();
                            else {
                                stage++;
                                interestStage();
                            }
                            break;
                        case 4: //interests
                            interests = "";
                            for (int i = 0; i < 5; i++) {
                                interests += (interestsFlag[i]) ? "1" : "0";
                            }
                            SetInfo setInfo = new SetInfo(email, name, majorType, major, interests, FirstLoginActivity.this, FirstLoginActivity.this);
                            setInfo.execute();
                        default:
                    }
                } catch (NullPointerException e) {
                    Log.e("err",e.getMessage());
                    Toast.makeText(FirstLoginActivity.this, "빈칸이군요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        majorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(stage == 2){
                    majorType = new Integer(position).toString();
                    if(majorType.equals("0")){
                        firstLoginButton.setVisibility(View.INVISIBLE);
                        firstLoginButton.clearAnimation();
                    }
                    else {
                        firstLoginButton.setVisibility(View.VISIBLE);
                        firstLoginButton.startAnimation(fadeIn);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void welcomeStage() {
        clearStage();
        changingEditText.setHint("최대 10자");
        changingText.setText("환영합니다!");
        changingText.startAnimation(fadeIn);
    }
    public void nameStage() {
        clearStage();
        changingEditText.setText(null);
        changingEditText.setVisibility(View.VISIBLE);
        firstLoginButton.setVisibility(View.VISIBLE);
        changingText.setText("별명을 설정해주세요!");
        changingText.startAnimation(fadeIn);
        changingEditText.startAnimation(fadeIn);
        firstLoginButton.startAnimation(fadeIn);
    }
    public void majorTypeStage() {
        clearStage();
        majorTypeSpinner.setVisibility(View.VISIBLE);
        changingText.setText("어떤 계열이셨나요?");
        changingText.startAnimation(fadeIn);
        majorTypeSpinner.startAnimation(fadeIn);
        if(!majorType.equals("0")){
            firstLoginButton.setVisibility(View.VISIBLE);
            firstLoginButton.startAnimation(fadeIn);
        }
    }
    public void majorStage() {
        clearStage();
        firstLoginButton.setVisibility(View.VISIBLE);
        changingEditText.setVisibility(View.VISIBLE);
        changingEditText.setHint("최대 20자");
        String temp;
        switch (majorType){
            case "5":
            case "6":
                temp = "관심분야가 뭔가요?";
                break;
            default:
                temp = "학과 이름이 무엇인가요?";
        }
        changingText.setText(temp);
        changingText.startAnimation(fadeIn);
        changingEditText.startAnimation(fadeIn);
        firstLoginButton.startAnimation(fadeIn);
    }
    public void interestStage() {
        clearStage();
        interestRecycler.setVisibility(View.VISIBLE);
        changingText.setText("무엇을 하고 싶나요?");
        changingText.startAnimation(fadeIn);
        interestRecycler.startAnimation(fadeIn);
    }
    public void clearStage() {
        firstLoginButton.setVisibility(View.INVISIBLE);
        changingEditText.setVisibility(View.INVISIBLE);
        majorTypeSpinner.setVisibility(View.INVISIBLE);
        interestRecycler.setVisibility(View.INVISIBLE);
        firstLoginButton.clearAnimation();
        changingEditText.clearAnimation();
        majorTypeSpinner.clearAnimation();
        interestRecycler.clearAnimation();
    }
    public void closeKeyboard() {
        if (getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(changingEditText.getWindowToken(), 0);
    }
}
