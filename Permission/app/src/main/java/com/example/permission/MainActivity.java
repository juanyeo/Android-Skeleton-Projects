package com.example.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    String [] permission_list = {Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textview);
        checkPermission();
    }

    public void checkPermission() {
        // 현재 안드로이드 버전이 6.0 미만이면 권한 설정 기능이 없으므로 메서드를 종료한다
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { return;}
        // 각 권한의 허용 여부를 확인한다
        for (String permission: permission_list) {
            int chk = checkCallingOrSelfPermission(permission);
            if (chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission_list, 0);
            }
        }
    }

    // 권한 허용을 물어봤을 때 어떤 권한이 허용되었는지 확인할 수 있다
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i=0; i<grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                text1.append(permissions[i] + " : 허용\n");
            } else {
                text1.append(permissions[i] + " : 거부\n");
            }
        }
    }
}