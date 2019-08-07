package com.example.untitledproject;

public class FinishTimer extends Thread {
    int activity = 0;
    LoginActivity loginActivity;
    MainActivity mainActivity;
    public FinishTimer(LoginActivity me) {
        loginActivity = me;
        activity = 1;
    }
    public FinishTimer(MainActivity me) {
        mainActivity = me;
        activity = 2;
    }
    public void run() {
        try {
            Thread.sleep(2000);
            switch (activity) {
                case 1:
                    loginActivity.stage = 0;
                    break;
                case 2:
                    mainActivity.stage = 0;
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
