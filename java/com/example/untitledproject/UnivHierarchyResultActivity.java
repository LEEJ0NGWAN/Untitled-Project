package com.example.untitledproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UnivHierarchyResultActivity extends AppCompatActivity {
    TextView hierarchyName, hierarchyTier, hierarchyDetail;
    Button buttonReturn;
    String univName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univhierarchyresult);
        hierarchyName = (TextView) findViewById(R.id.hierarchyName);
        hierarchyTier = (TextView) findViewById(R.id.hierarchyTier);
        hierarchyDetail = (TextView) findViewById(R.id.hierarchyDetail);
        buttonReturn = (Button) findViewById(R.id.buttonReturn);
        Intent intentFromInputActivity = getIntent();
        univName = intentFromInputActivity.getExtras().getString("univName");
        univName.trim();
        checkAndSetUnivText(univName);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void checkAndSetUnivText(String univName){
        if(univName.equals("서울")){
            hierarchyName.setText("왕족");
            hierarchyTier.setText("1 티어");
            hierarchyDetail.setText("당신은 위대한 왕의 힘을 가진 혈통입니다.");
        }
        else if(univName.equals("연세") || univName.equals("고려")){
            hierarchyName.setText("공작");
            hierarchyTier.setText("2 티어");
            hierarchyDetail.setText("당신은 왕의 뒤를 잇는 힘을 가졌으며, 가장 강한 귀족의 혈통입니다.");
        }
        else if(univName.equals("서강") || univName.equals("성균관") || univName.equals("한양")){
            hierarchyName.setText("후작");
            hierarchyTier.setText("3 티어");
            hierarchyDetail.setText("당신은 눈에 띌 정도로 강한 힘을 가진 귀족 혈통입니다.");
        }
        else if(univName.equals("중앙") || univName.equals("경희") || univName.equals("서울시립") || univName.equals("건국")){
            hierarchyName.setText("백작");
            hierarchyTier.setText("4 티어");
            hierarchyDetail.setText("당신은 어느 정도 힘을 가진 귀족 혈통입니다.\n같은 작위임에도 힘의 차이가 가장 많이 나는 계급이기도 합니다.");
        }
        else if(univName.equals("동국") || univName.equals("홍익")){
            hierarchyName.setText("자작");
            hierarchyTier.setText("5 티어");
            hierarchyDetail.setText("당신은 평범한 귀족 혈통입니다.");
        }
        else{
            hierarchyName.setText("천민");
            hierarchyTier.setText("최하위 티어");
            hierarchyDetail.setText("당신은 근본, 계보, 혈통도 없는, 어디에나 흔히 보이는 천민입니다.");
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
