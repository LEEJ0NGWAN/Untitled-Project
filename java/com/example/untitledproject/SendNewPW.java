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

public class SendNewPW extends AsyncTask {
    boolean successFlag = false;
    String data = null;
    String email, code;
    ForgotActivity forgotActivity;
    Context forgotActivityContext;
    public SendNewPW(String userEmail, String inputCode, Context context, ForgotActivity me){
        email = userEmail;
        code = inputCode;
        forgotActivity = me;
        forgotActivityContext = context;
    }
    @Override
    protected Void doInBackground(Object[] objects) {
        String query =  "userMail=" + email + "&code=" + code;
        try {
            /* 서버연결 */
            String urlStr = ServerURL.getHost() + "setNewPWAndSend.php";
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
                title = "";
                msg = "메일로 새로운 비밀번호를 보냈습니다!\n로그인 후 원하시는 비밀번호로 변경해주세요! :D";
                btnText = "확인";
                successFlag = true;
                break;
            case "-1":
                title = "";
                msg = "인증코드 확인해주세요!\n인증코드 재발신도 가능합니다!";
                btnText = "젠장";
                break;
            default:
                title = "";
                msg = "에러코드 : " + data;
                btnText = "확인";
        }
        Log.e("RESULT",msg);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(forgotActivityContext);
        alertBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(successFlag)
                            forgotActivity.finish();
                    }
                });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}