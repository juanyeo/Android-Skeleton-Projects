package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        text1 = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        ObjectClass obj = intent.getParcelableExtra("object1");
        text1.setText("객체 데이터를 받았습니다\n");
        text1.append("obj.data10 = " + obj.data10 + "\n");
        text1.append("obj.data20 = " + obj.data20 + "\n");
    }

    public void finishActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("int1", 200);
        intent.putExtra("boolean1",true);
        intent.putExtra("string1","문자열 2");

        setResult(RESULT_OK, intent);
        finish();
    }
}