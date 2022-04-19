package com.example.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch(action) {
            case "android.intent.action.BOOT_COMPLETED":
                Toast t1 = Toast.makeText(context, "시스템 재시작 알림 - Intent 앱", Toast.LENGTH_LONG);
                t1.show();
                break;
        }
    }
}