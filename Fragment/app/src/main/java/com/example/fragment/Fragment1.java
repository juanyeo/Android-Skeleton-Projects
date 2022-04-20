package com.example.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Fragment1 extends Fragment {

    EditText edit1;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        Button button1 = (Button) view.findViewById(R.id.button);
        edit1 = (EditText) view.findViewById(R.id.editText1);
        Button1Listener listener = new Button1Listener();
        button1.setOnClickListener(listener);

        return view;
    }

    class Button1Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();

            String text = edit1.getText().toString();
            if(text != null) {
                activity.list_data.add(text);
            }
        }
    }
}