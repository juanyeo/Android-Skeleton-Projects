package com.example.intent;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

public class IntentService1 extends IntentService {

    boolean isRunning = true;

    public IntentService1() {
        super("IntentService1");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while(isRunning) {
            SystemClock.sleep(1000);
            long time = System.currentTimeMillis();
            Log.d("service", "Intent Service Running... : " + time);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}