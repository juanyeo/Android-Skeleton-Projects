package com.example.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ButtonFragment fragmentB = new ButtonFragment();
    Fragment1 fragment1 = new Fragment1();
    ListFragment1 fragment2 = new ListFragment1();

    ArrayList<String> list_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment("fragmentB");

        list_data.add("항목 1");
        list_data.add("항목 2");
        list_data.add("항목 3");
    }

    public void setFragment(String name) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        switch(name) {
            case "fragmentB":
                Log.d("fragfrag","BBBBBB");
                tran.replace(R.id.frame1, fragmentB);
                break;
            case "fragment1":
                tran.replace(R.id.frame2, fragment1);
                tran.addToBackStack(null);
                break;
            case "fragment2":
                tran.replace(R.id.frame2, fragment2);
                tran.addToBackStack(null);
                break;
            case "fragment3":
                DialogFragment1 fragment3 = new DialogFragment1();
                fragment3.show(manager, "tag");
                break;
        }
        tran.commit();
    }

    public void dialogResult(String result) {
        ButtonFragment buttonFragment = (ButtonFragment) getSupportFragmentManager().findFragmentById(R.id.frame1);
        buttonFragment.setFragmentText(result);
    }
}