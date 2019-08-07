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

public class NameCheck extends AsyncTask {
    boolean successFlag = false;
    String data = null;
    String userName;
    FirstLoginActivity firstLoginActivity;
    Context firstLoginActivityContext;
    public NameCheck(String name, Context context, FirstLoginActivity me){
        userName = name;
        firstLoginActivity = me;
        firstLoginActivityContext = context;
    }
    @Override
    protected Void doInBackground(Object[] objects) {
        String query =  "userName=" + userName;
        try {
            /* 서버연결 */
            String urlStr = ServerURL.getHost() + "nameCheck.php";
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
        String title = null, msg = null, btnText = null;
        switch (data) {
            case "0":
                title = "오우 이런";
                msg = "이미 존재하는 닉네임이군요!";
                btnText = "젠장";
                break;
            case "1":
                successFlag = true;
            default:
                title = "오류";
                msg = "에러코드 : " + data;
                btnText = "확인";
        }
        Log.e("RESULT",msg);
        if(!successFlag){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(firstLoginActivityContext);
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
        }
        else{
            firstLoginActivity.stage++;
            firstLoginActivity.majorTypeStage();
        }
    }
}