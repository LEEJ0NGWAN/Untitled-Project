package com.example.untitledproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class NewJoin extends AsyncTask {
    boolean successFlag = false;
    String data = null;
    String email, pw;
    JoinActivity joinActivity;
    Context joinActivityContext;
    public NewJoin(String userEmail, String userPassword, Context context, JoinActivity me){
        email = userEmail;
        pw = userPassword;
        joinActivity = me;
        joinActivityContext = context;
    }
    @Override
    protected Void doInBackground(Object[] objects) {
        String query =  "userMail=" + email + "&userPW=" + pw + "&hostAddr=" + ServerURL.getHost();
        try {
            /* 서버연결 */
            String urlStr = ServerURL.getHost() + "userNewJoin.php";
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
                msg = "이메일 주소로 인증코드를 발신했습니다!\n수신하셔서 인증을 완료해주세요";
                btnText = "확인";
                successFlag = true;
                break;
            case "1062":
                title = "오우 이런!";
                msg = "이미 등록된 메일 주소입니다 :(";
                btnText = "젠장";
                break;
            default:
                title = "오류";
                msg = "에러코드 : " + data;
                btnText = "확인";
        }
        Log.e("RESULT",msg);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(joinActivityContext);
        alertBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(successFlag)
                            joinActivity.finish();
                    }
                });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}