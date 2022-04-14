package com.example.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView2;
    Button button1;
    CheckBox checkbox1, checkbox2, checkbox3;
    RadioGroup radiogroup1;
    RadioButton radio1, radio2;
    ProgressBar progressBar2;
    SeekBar seekBar1, seekBar2;
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LifeCycle","onCreate()");
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView2);
        button1 = (Button) findViewById(R.id.button1);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkBox3);
        radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        radio1 = (RadioButton) findViewById(R.id.radioButton);
        radio2 = (RadioButton) findViewById(R.id.radioButton2);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        textView2.setText("set in JAVA");
        button1.setOnClickListener(new Button1Listener());
        checkbox1.setOnCheckedChangeListener(new CheckboxListener());
        radiogroup1.setOnCheckedChangeListener(new RadioListener());
        seekBar1.setOnSeekBarChangeListener(new SeekBarListener());
        seekBar2.setOnSeekBarChangeListener(new SeekBarListener());
        editText1.setOnEditorActionListener(new EnterListener());
        editText2.setText("Default");
        editText2.addTextChangedListener(new EditListener());
    }

    class Button1Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            textView2.setText("Button 1 Clicked");
            int radioId = radiogroup1.getCheckedRadioButtonId();
            switch (radioId) {
                case R.id.radioButton:
                    Log.d("radio","Radio Button 1 Checked");
                    break;
                case R.id.radioButton2:
                    Log.d("radio","Radio Button 2 Checked");
                    break;
            }
            progressBar2.incrementProgressBy(10);
        }
    }

    public void button2Function(View view) {
        textView2.setText("Button 2 Clicked");
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.toggle();
        radio2.setChecked(true);
        progressBar2.setProgress(40);
    }

    class CheckboxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d("event","Checkbox Checked");
        }
    }

    class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d("radio","Radio Group Listener");
        }
    }

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int seekBarId = seekBar.getId();
            int seekProgress = seekBar.getProgress();
            switch (seekBarId) {
                case R.id.seekBar:
                    textView2.setText("Seek 1: " + seekProgress);
                case R.id.seekBar2:
                    textView2.setText("Seek 2: " + seekProgress);
            }
            Log.d("seekbar","Progress Changed");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.d("seekbar","Start Touch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d("seekbar","Stop Touch");
        }
    }

    class EnterListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            textView2.setText("Edit 1: " + v.getText().toString());
            return false;
        }
    }

    class EditListener implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textView2.setText("Edit 2: " + editText2.getText().toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle","onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","onDestroy()");
    }
}
