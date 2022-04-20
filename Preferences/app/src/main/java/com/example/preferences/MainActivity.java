package com.example.preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SettingFragment settingFragment = new SettingFragment();
    ResultFragment resultFragment = new ResultFragment();
    String currentFragment = "setting";
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment("setting");
        text1 = (TextView) findViewById(R.id.text1);
    }

    public void setFragment(String name) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        switch(name) {
            case "setting":
                tran.replace(R.id.frame, settingFragment);
                break;
            case "result":
                tran.replace(R.id.frame, resultFragment);
                break;
        }
        tran.commit();
    }

    public void saveButtonClicked(View view) {
        SharedPreferences pref = getSharedPreferences("pref1", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("intData", 100);
        editor.putString("stringData", "문자열1");

        HashSet<String> set = new HashSet<String>();
        set.add("item1");
        set.add("item2");
        set.add("item3");
        editor.putStringSet("stringSetData", set);

        editor.commit();
    }

    public void readButtonClicked(View view) {
        SharedPreferences pref = getSharedPreferences("pref1", MODE_PRIVATE);

        int intData = pref.getInt("intData", 10);
        String stringData = pref.getString("stringData", "No String");
        Set<String> stringSetData = pref.getStringSet("stringSetData", null);

        text1.setText("저장된 데이터 \n");
        text1.append("Int : " + intData + ", ");
        text1.append("String : " + stringData + ", ");
        text1.append("Set : ");
        for(String str: stringSetData) {
            text1.append(str + " ");
        }
    }

    public void changeFragmentButtonClicked(View view) {
        if(currentFragment == "setting") {
            currentFragment = "result";
        } else if(currentFragment == "result") {
            currentFragment = "setting";
        }

        setFragment(currentFragment);
    }

}