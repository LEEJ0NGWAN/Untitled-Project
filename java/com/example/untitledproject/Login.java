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

public class Login extends AsyncTask {
    boolean successFlag = false, firstLoginFlag = false;
    String data = null;
    String email, pw, name;
    LoginActivity loginActivity;
    Context loginActivityContext;
    public Login(String userEmail, String userPassword, Context context, LoginActivity me){
        email = userEmail;
        pw = userPassword;
        loginActivity = me;
        loginActivityContext = context;
    }
    @Override
    protected Void doInBackground(Object[] objects) {
        String query =  "userMail=" + email + "&userPW=" + pw;
        try {
            /* 서버연결 */
            String urlStr = ServerURL.getHost() + "userLogin.php";
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
            name = bufferedReader.readLine();
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
        String title = null, msg = null, btnText = null;
        switch (data) {
            case "noUser":
            case "notCorrectPW":
                title = "오우 맙소사";
                msg = "주소 또는 암호가 잘못되었군요!";
                btnText = "확인";
                break;
            case "notVerified":
                title = "오우 이런";
                msg = "인증된 계정이 아니군요! 메일을 확인해 주세요!";
                btnText = "확인";
                break;
            case "notInitialized":
                firstLoginFlag = true;
            case "loginSuccess":
                successFlag = true;
            default:
                title = "오류";
                msg = "에러코드 : " + data;
                btnText = "확인";
        }
        Log.e("RESULT",msg);
        if(!successFlag){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(loginActivityContext);
            alertBuilder
                    .setTitle(title)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } else if(firstLoginFlag && successFlag){
            Intent intentToFirstLoginActivity = new Intent(loginActivityContext,FirstLoginActivity.class);
            intentToFirstLoginActivity.putExtra("email", email);
            loginActivity.startActivity(intentToFirstLoginActivity);
        } else if(successFlag){
            Intent intentToMainActivity = new Intent(loginActivityContext, MainActivity.class);
            intentToMainActivity.putExtra("email", email);
            intentToMainActivity.putExtra("name", name);
            loginActivity.startActivity(intentToMainActivity);
        }
    }
}