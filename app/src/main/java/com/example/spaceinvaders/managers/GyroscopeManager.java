package com.example.spaceinvaders.managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Arrays;

public class GyroscopeManager implements SensorEventListener {
    private final SensorManager manager;
    private final Sensor gyroscope;
    private float[] values = new float[3];
    boolean reset = false;

    GyroscopeManager(Context context){
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void register(){
        manager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregister(){
        manager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(reset){
            values = new float[3];
            reset = false;
        }
        values[0] += event.values[0];
        values[1] += event.values[1];
        values[2] += event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float[] getGyroscopeValues(){
        if(!reset){
            float[] copy = Arrays.copyOf(values, 3);
            reset = true;
            return copy;
        }
        return null;
    }
}
