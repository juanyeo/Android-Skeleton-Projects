package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    ImageView image1;

    SensorManager manager;
    SensorListener listener;
    boolean isOrientation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text1);
        image1 = (ImageView) findViewById(R.id.imageView);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listener = new SensorListener();
    }

    public void buttonClicked(View view) {
        manager.unregisterListener(listener);
        isOrientation = false;
        image1.setVisibility(View.GONE);
        Sensor sensor;

        switch (view.getId()) {
            case R.id.sensor_list:
                List<Sensor> sensorList = manager.getSensorList(Sensor.TYPE_ALL);
                text1.setText("센서 목록\n\n");
                for (Sensor s : sensorList) {
                    text1.append(s.getName() + "\n");
                }
            break;
            case R.id.accelerometer:
                sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.light:
                sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.gyroscope:
                sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.magnetic_field:
                sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.orientation:
                isOrientation = true;
                image1.setVisibility(View.VISIBLE);
                Sensor sensor1 = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Sensor sensor2 = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

                manager.registerListener(listener, sensor1, SensorManager.SENSOR_DELAY_UI);
                manager.registerListener(listener, sensor2, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.pressure:
                sensor = manager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
            case R.id.distance:
                sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                break;
        }
    }

    class SensorListener implements SensorEventListener {

        float[] accValue = new float[3];
        float[] magValue = new float[3];

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    if (isOrientation == false) {
                        text1.setText("가속도 센서(Accelerometer)\n\n");
                        text1.append("X축 기울기 : " + event.values[0] + "\n");
                        text1.append("Y축 기울기 : " + event.values[1] + "\n");
                        text1.append("Z축 기울기 : " + event.values[2]);
                    } else {
                        System.arraycopy(event.values, 0, accValue, 0, event.values.length);
                    }
                    break;
                case Sensor.TYPE_LIGHT:
                    text1.setText("조도 센서(Light)\n\n");
                    text1.append("주변 밝기 : " + event.values[0] + "lux");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    text1.setText("자이로 스코프 센서(Gyroscope)\n\n");
                    text1.append("X축 각속도 : " + event.values[0] + "\n");
                    text1.append("Y축 각속도 : " + event.values[1] + "\n");
                    text1.append("Z축 각속도 : " + event.values[2]);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    if (isOrientation == false) {
                        text1.setText("마그네틱 필드 센서(Magnetic Field)\n\n");
                        text1.append("X축 자기장 : " + event.values[0] + "\n");
                        text1.append("Y축 자기장 : " + event.values[1] + "\n");
                        text1.append("Z축 자기장 : " + event.values[2]);
                    } else {
                        System.arraycopy(event.values, 0, magValue, 0, event.values.length);
                    }
                    break;
                case Sensor.TYPE_PRESSURE:
                    text1.setText("기압 센서(Pressure)\n\n");
                    text1.append("현재 기압 : " + event.values[0] + "millibar");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    text1.setText("근접 센서(Proximity)\n\n");
                    text1.append("물체와의 거리 : " + event.values[0] + "cm");
                    break;
            }

            if (isOrientation == true) {
                float[] R = new float[9];
                float[] I = new float[9];

                SensorManager.getRotationMatrix(R, I, accValue, magValue);

                float[] values = new float[3];
                SensorManager.getOrientation(R, values);

                float azimuth = (float) Math.toDegrees(values[0]);
                float pitch = (float) Math.toDegrees(values[1]);
                float roll = (float) Math.toDegrees(values[2]);
                if (azimuth < 0) {
                    azimuth += 360;
                }

                text1.setText("방위 계산 결과(Orientation)\n\n");
                text1.append("방위값 : " + azimuth + "\n");
                text1.append("좌우 기울기 값 : " + pitch + "\n");
                text1.append("앞뒤 기울기 값 : " + roll);
                image1.setRotation(-azimuth);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}