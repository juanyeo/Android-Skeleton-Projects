package com.example.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

// Thread 는 일을 처리하는 노동자, Main Thread 는 화면 구성에 관련된 처리를 하며 UI Thread 라고도 불린다
// Main Thread 는 바빠서 오랜 시간이 걸리는 일을 동시에 수행할 수 없다. 상하차와 라벨링을 동시에 할 수 없듯이
// 오래 걸리거나 반복적인 일은 다른 Thread 가 처리하도록 한다. 네트워크는 무조건 다른 Thread 에서 처리해야 한다
// 다른 Thread 에서 UI 수정을 하면 오류가 발생한다(Android 8.0 미만) Handler 를 사용, Main Thread 에 요청해야한다
// Thread 와 Main Thread 를 연결하는 데에는 세가지 방식이 있다
// 1. Handler
// 2. AsyncTask
// 3. runOnUiThread
public class MainActivity extends AppCompatActivity {

    TextView text1,text2,text3;
    Handler handler;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        isRunning = true;

        Thread1Class thread1 = new Thread1Class();
        handler = new Handler1Class();
        thread1.start();

        AsyncTaskClass async = new AsyncTaskClass();
        async.execute(20,200);

        Thread2Class thread2 = new Thread2Class();
        thread2.start();
    }

    class Thread1Class extends Thread {
        @Override
        public void run() {
            int int1 = 10;
            int int2 = 100;
            while (isRunning) {
                SystemClock.sleep(1000);
                handler.sendEmptyMessage(0);
                SystemClock.sleep(1000);
                handler.sendEmptyMessage(1);

                // Handler(Main Thread) 에 화면 처리를 위한 데이터를 넘기는 경
                SystemClock.sleep(1000);
                long now = System.currentTimeMillis();
                Message msg = new Message();
                msg.what = 2;
                msg.arg1 = ++int1;
                msg.arg2 = ++int2;
                msg.obj = now;
                handler.sendMessage(msg);
            }
        }
    }

    class Handler1Class extends Handler {
        // handler.sendEmptyMessage(what: Int) 로 호출된다
        // Int 변수 2개, Object 하나를 넣을 수 있다
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    text1.setText("Handler 작업 1");
                    break;
                case 1:
                    text1.setText("Handler 작업 2");
                    break;
                case 2:
                    text1.setText("Handler 작업 3 - " + msg.arg1 + ", " + msg.arg2 + " /" + msg.obj);
                    break;
            }
        }
    }

    class AsyncTaskClass extends AsyncTask<Integer,Long,String> {

        // doInBackground 함수 이전에 호출, Main Thread 에서 실행: UI 작업 O, 오래걸리는 작업 X
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 일반 Thread 에서 실행, 오래 걸리는 작업은 여기에서 한다
        // UI 작업이 필요하면 publishProgress(obj) 로 onProgressUpdate(obj) 호출
        @Override
        protected String doInBackground(Integer... integers) {
            int arg1 = integers[0];
            int arg2 = integers[1];

            for (int i=0; i<10; i++) {
                SystemClock.sleep(800);
                arg1 += 10;
                arg2 += 100;
                long obj = arg1 + arg2;
                publishProgress(obj);
            }
            return "AsyncTask 완";
        }

        // doInBackground 함수 실행 중 해야 할 UI 작업을 Main Thread 로 전달한다
        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            text2.setText("AsyncTask - " + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            text2.setText(s);
        }
    }

    class Thread2Class extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                SystemClock.sleep(700);
                final long time = System.currentTimeMillis();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text3.setText("runOnUiThread - " + time);
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}