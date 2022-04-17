package com.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pro;

    String [] data1 = {"항목1","항목2","항목3","항목4","항목5","항목6","항목7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toastButtonMethod1(View view) {
        Toast t1 = Toast.makeText(this, "Toast Message",Toast.LENGTH_LONG);
        t1.show();
    }

    public void toastButtonMethod2(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View custom_view = inflater.inflate(R.layout.custom_toast, null);
        Toast t2 = new Toast(this);
        ImageView img1 = (ImageView) custom_view.findViewById(R.id.imageView1);
        img1.setImageResource(R.drawable.kakaobear);
        t2.setView(custom_view);
        t2.show();
    }

    public void dialogButtonMethod1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("제목");
        builder.setMessage("본문입니다");
        builder.setIcon(R.mipmap.ic_launcher);
        Dialog1Listener listener = new Dialog1Listener();
        builder.setPositiveButton("Positive",listener);
        builder.setNegativeButton("Negative", null);
        builder.setNeutralButton("Neutral",null);
        builder.show();
    }

    class Dialog1Listener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Toast.makeText(getBaseContext(),"확인",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public void dialogButtonMethod2(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("커스텀 다이얼로그");
        builder.setIcon(R.mipmap.ic_launcher);

        LayoutInflater inflater = getLayoutInflater();
        View custom_view = inflater.inflate(R.layout.dialog, null);
        builder.setView(custom_view);
        Dialog2Listener listener2 = new Dialog2Listener();
        builder.setPositiveButton("확인",listener2);
        builder.show();
    }

    class Dialog2Listener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog) dialog;
            EditText edit1 = (EditText) alert.findViewById(R.id.editText1);
            EditText edit2 = (EditText) alert.findViewById(R.id.editText2);
            String str = edit1.getText().toString() + edit2.getText().toString();
            Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogButtonMethod3(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Dialog3Listener listener3 = new Dialog3Listener();
        DatePickerDialog picker = new DatePickerDialog(this, listener3, year, month, day);
        picker.show();
    }

    class Dialog3Listener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Toast.makeText(getBaseContext(),year+"년 "+(month+1)+"월 "+dayOfMonth+"일",Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogButtonMethod4(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        Dialog4Listener listener4 = new Dialog4Listener();
        TimePickerDialog picker = new TimePickerDialog(this, listener4, hour, minute, false);
        picker.show();
    }

    class Dialog4Listener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Toast.makeText(getBaseContext(),hourOfDay+"시 "+minute+"분",Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogButtonMethod5(View view) {
        pro = ProgressDialog.show(this, "제목", "본문입니다");
        Handler handler = new Handler();
        ThreadClass thread = new ThreadClass();
        //5초
        handler.postDelayed(thread, 5000);
    }

    class ThreadClass extends Thread {
        @Override
        public void run() {
            pro.cancel();
        }
    }

    public void dialogButtonMethod6(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("리스트 다이얼로그");
        builder.setNegativeButton("취소",null);
        Dialog5Listener listener5 = new Dialog5Listener();
        // 리스트 다이얼로그를 사용할 때
        builder.setItems(data1, listener5);
        builder.show();
    }

    class Dialog5Listener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // which 변수에 position 값
        }
    }

    public void NotificationMethod1(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("channel1", "첫번째 채널");

        builder.setNumber(5);
        builder.setContentTitle("노티 제목");
        builder.setContentText("본문입니다");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 알림 메시지의 값이 같을 경우 덮어 쓰고 다르면 새 메시지로 계속 출력된다
        manager.notify(10,notification);
    }

    public void NotificationMethod2(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("pending","pending intent");
        builder.setContentTitle("Pending Intent Noti");
        builder.setContentText("Click and move to another Activity");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        //메시지를 터치하면 실행될 Activity 정보를 관리할 객체
        Intent intent1 = new Intent(this,Test1Activity.class);
        intent1.putExtra("data1", "Activity 1 - Main에서 보낸 데이터");
        PendingIntent pending1 = PendingIntent.getActivity(this, 10, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pending1);
        // 확인 후 자동으로 없어진다
        //builder.setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(20,notification);
    }

    public void NotificationMethod3(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("pending","pending intent");
        builder.setContentTitle("Pending Intent Noti");
        builder.setContentText("Click and move to another Activity");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        Intent intent2 = new Intent(this,Test2Activity.class);
        PendingIntent pending2 = PendingIntent.getActivity(this, 10, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action.Builder builder2 = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_compass,"Action 2", pending2);
        NotificationCompat.Action action2 = builder2.build();
        builder.addAction(action2);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(20,notification);
    }

    public void NotificationMethod4(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("style","big picture");
        builder.setContentTitle("Big Picture Style");
        builder.setContentText("Content Text");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        NotificationCompat.BigPictureStyle big = new NotificationCompat.BigPictureStyle(builder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kakaobear);
        big.bigPicture(bitmap);
        big.setBigContentTitle("Big Content Title");
        big.setSummaryText("Summary Test");

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(30,notification);
    }

    public void NotificationMethod5(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("style","big text");
        builder.setContentTitle("Big Text Style");
        builder.setContentText("Content Text");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        NotificationCompat.BigTextStyle big = new NotificationCompat.BigTextStyle(builder);
        big.setSummaryText("Summary Text");
        big.setBigContentTitle("Title Big");
        big.bigText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세 무궁화 삼천리");

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(40,notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void NotificationMethod6(View view) {
        NotificationCompat.Builder builder = getNotificationBuilder("message","history");
        builder.setContentTitle("Message Style");
        builder.setContentText("Content Text");
        builder.setSmallIcon(android.R.drawable.ic_menu_add);

        Person.Builder person1 = new Person.Builder();
        IconCompat icon1 = IconCompat.createWithResource(this, android.R.drawable.ic_menu_compass);
        person1.setIcon(icon1);
        person1.setName("Kim");
        Person user1 = person1.build();

        Person.Builder person2 = new Person.Builder();
        IconCompat icon2 = IconCompat.createWithResource(this, android.R.drawable.ic_menu_camera);
        person2.setIcon(icon2);
        person2.setName("Park");
        Person user2 = person2.build();

        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(user1);
        style.addMessage("첫 번째 메시지",System.currentTimeMillis(), user1);
        style.addMessage("두 번째 메시지",System.currentTimeMillis(),user2);
        style.addMessage("세 번째 메시지",System.currentTimeMillis(),user1);
        style.addMessage("네 번째 메시지",System.currentTimeMillis(),user2);
        builder.setStyle(style);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(50,notification);
    }

    // 안드로이드 8.0 이상을 대응하기 위한 Notification.Builder 메서드
    public NotificationCompat.Builder getNotificationBuilder(String id, String name) {
        // id : Notification 을 그룹화 하기 위한 Channel
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 안드로이드 8.0 오레오 버전 이상
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(id,name,NotificationManager.IMPORTANCE_HIGH);
            //메시지 출력 시 단말기 LED를 사용할 것인지 / 진동을 사용할 것인지
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            // 채널 등록
            manager.createNotificationChannel(channel);
            // 메서드 생성을 위한 객체 생성
            builder = new NotificationCompat.Builder(this, id);
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        return builder;
    }
}