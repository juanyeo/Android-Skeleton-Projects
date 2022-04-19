package com.example.intent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class Service2 extends Service {

    boolean isRunning = true;

    public Service2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Notification Code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("test1", "Service", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test1");
            builder.setContentTitle("Foreground Service Running");
            builder.setAutoCancel(true);
            Notification notification = builder.build();

            startForeground(10, notification);
        }

        Log.d("service","서비스 1 시작");
        Service2.ThreadClass thread = new Service2.ThreadClass();
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(STOP_FOREGROUND_REMOVE);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(10);
            }
        }
    }
}