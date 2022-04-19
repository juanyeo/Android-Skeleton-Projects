package com.example.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text2;
    Button button7,button8,button9;
    String [] permission_list = {Manifest.permission.CALL_PHONE};
    Intent service1_intent, service2_intent, service3_intent;
    boolean isService1Running = false;
    boolean isService2Running = false;
    boolean isService3Running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        text2 = (TextView) findViewById(R.id.textView2);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
    }

    public void openBrowser(View view) {
        Uri uri = Uri.parse("http://developer.android.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openMap(View view) {
        Uri uri = Uri.parse("geo:37.243243,131.861601");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void callPhone(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int chk = checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
            if (chk == PackageManager.PERMISSION_DENIED) {return;}
        }

        Uri uri = Uri.parse("tel:00000000000");
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        startActivity(intent);
    }

    public void openOtherApp(View view) {
        Intent intent = new Intent("com.test.basic");
        startActivity(intent);
    }

    public void openActivity(View view) {
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    public void openActivitySendData(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        ObjectClass obj = new ObjectClass();
        obj.data10 = 100;
        obj.data20 = "객체 문자열";

        intent.putExtra("object1", obj);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                int value1 = data.getIntExtra("int1",0);
                boolean value2 = data.getBooleanExtra("boolean1",false);
                String value3 = data.getStringExtra("string1");
                text2.setText("Data from Activity 2 - " + value1 + ", " + value2 + ", " + value3);
                break;
        }
    }

    public void runService(View view) {
        if (isService1Running) {
            stopService(service1_intent);
            button7.setText("서비스 실행");
            isService1Running = false;
        } else {
            service1_intent = new Intent(this, Service1.class);
            startService(service1_intent);
            button7.setText("서비스 중지");
            isService1Running = true;
        }
    }

    public void runIntentService(View view) {
        if (isService2Running) {
            stopService(service2_intent);
            button8.setText("인텐트 서비스 실행");
            isService2Running = false;
        } else {
            service2_intent = new Intent(this, IntentService1.class);
            startService(service2_intent);
            button8.setText("인텐트 서비스 중지");
            isService2Running = true;
        }
    }

    public void runForegroundService(View view) {
        if (isService3Running) {
            stopService(service3_intent);
            button9.setText("포그라운드 서비스 실행");
            isService3Running = false;
        } else {
            service3_intent = new Intent(this, Service2.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(service3_intent);
            } else {
                startService(service3_intent);
            }
            button9.setText("포그라운드 서비스 중지");
            isService3Running = true;
        }
    }



    public void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { return; }
        for (String permission: permission_list) {
            int chk = checkCallingOrSelfPermission(permission);
            if (chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission_list, 0);
                break;
            }
        }
    }


}