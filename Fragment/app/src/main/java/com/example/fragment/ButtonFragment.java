package com.example.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ButtonFragment extends Fragment {

    Button button2, button3, button4;
    TextView text5;

    public ButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
        text5 = (TextView) view.findViewById(R.id.textView5);

        ButtonListener listener = new ButtonListener();
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);

        return view;
    }

    public void setFragmentText(String text) {
        text5.setText(text);
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();

            int viewId = v.getId();
            switch(viewId) {
                case R.id.button2:
                    activity.setFragment("fragment1");
                    break;
                case R.id.button3:
                    activity.setFragment("fragment2");
                    break;
                case R.id.button4:
                    activity.setFragment("fragment3");
                    break;
            }
        }
    }
}