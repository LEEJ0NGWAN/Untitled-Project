package com.example.untitledproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SetInfo extends AsyncTask {
    boolean successFlag = false, firstSetFlag = false;
    String data = null;
    String email, name, majorType, major, interests;
    FirstLoginActivity firstLoginActivity;
    MainActivity mainActivity;
    Context context;
    public SetInfo(String userEmail, String userName, String userMajorType, String userMajor, String userInterests, Context context, FirstLoginActivity me){
        email = userEmail;
        name = userName;
        majorType = userMajorType;
        major = userMajor;
        interests = userInterests;
        this.context = context;
        firstLoginActivity = me;
        firstSetFlag = true;
    }
    public SetInfo(String userEmail, String userName, String userMajorType, String userMajor, String userInterests, Context context, MainActivity me){
        email = userEmail;
        name = userName;
        majorType = userMajorType;
        major = userMajor;
        interests = userInterests;
        this.context = context;
        mainActivity = me;
    }
    @Override
    protected Void doInBackground(Object[] objects) {
        String query =  "userMail=" + email + "&userName=" + name + "&userMajorType=" + majorType + "&userMajor=" + major + "&userInterests=" + interests;
        try {
            /* 서버연결 */
            String urlStr = ServerURL.getHost() + "setInfo.php";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            /* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(query.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);

//                while ( ( line = bufferedReader.readLine() ) != null )
//                    stringBuffer.append(line + "\n");

            data = bufferedReader.readLine();
            Log.e("RECV DATA",data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        /* 서버에서 응답 */
        Log.e("RECV DATA",data);
        String title, msg, btnText;
        switch (data) {
            case "0":
                title = "와우!";
                msg = "설정 완료 입니다!";
                btnText = "확인";
                successFlag = true;
                break;
            case "-1":
                title = "오우 이런!";
                msg = "변경할 사항이 없군요";
                btnText = "젠장";
                break;
            case "-2":
                title = "오우 이런!";
                msg = "메일 주소가 일치하지 않는군요";
                btnText = "젠장";
                break;
            default:
                title = "오류";
                msg = "에러코드 : " + data;
                btnText = "확인";
        }
        Log.e("RESULT",msg);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(successFlag && firstSetFlag){
                            Intent intentToMainActivity = new Intent(context, MainActivity.class);
                            intentToMainActivity.putExtra("email", email);
                            intentToMainActivity.putExtra("name", name);
                            firstLoginActivity.startActivity(intentToMainActivity);
                            firstLoginActivity.finish();
                        }
                    }
                });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}