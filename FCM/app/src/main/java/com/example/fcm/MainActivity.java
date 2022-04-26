package com.example.fcm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


// Firebase Cloud Messaging : 단말기 토큰 값을 서버로 전송 -> 서버가 FCM 서버로 요청 전송 -> FCM 서버가 단말기로
// google firebase console 에 프로젝트 생성 - 1. build.gradle 의 applicationId 입력,
// 2. 구성 파일 다운로드 후 app 폴더에 추가, 3. build.gradle 에 SDK implements 추가, 4. 앱 실행하여 설치 확인
// Firebase messaging 세팅 - 1. build.gradle 에 firebase-messaging implements 추가,
// 2. FirebaseMessagingService extends 한 클래스 생성 > onNewToken, onMessageReceived
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}