package com.example.intent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class Service1 extends Service {

    boolean isRunning = true;

    public Service1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service","서비스 1 시작");
        ThreadClass thread = new ThreadClass();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d("service","서비스 1 종료");
    }

    class ThreadClass extends Thread {
        @Override
        public void run() {
            while(isRunning) {
                SystemClock.sleep(1000);
                long time = System.currentTimeMillis();
                Log.d("service", "서비스 1 Running... : " + time);
            }
        }
    }
}