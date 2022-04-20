package com.example.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        TextView result_text = (TextView) v.findViewById(R.id.text_result);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        result_text.setText("Setting Result \n");

        boolean check_data = pref.getBoolean("check1", false);
        result_text.append("Check Box : " + check_data + "\n");

        String edit_data = pref.getString("edit1", null);
        result_text.append("Edit Text : " + edit_data + "\n");

        boolean switch_data = pref.getBoolean("switch1", false);
        result_text.append("Switch : " + check_data + "\n");

        String list_data = pref.getString("list1", null);
        result_text.append("List Select : " + list_data + "\n");

        Set<String> multi_data = pref.getStringSet("multi1", null);
        result_text.append("Multi Select : ");
        for(String str : multi_data) {
            result_text.append(str + ". ");
        }

        return v;
    }
}