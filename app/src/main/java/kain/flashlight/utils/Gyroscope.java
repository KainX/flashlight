package kain.flashlight.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Arrays;

/**
 * Gyroscope handler class
 */
public class Gyroscope {

    private SensorManager manager;
    private Sensor sensor;
    private Context context;
    private SensorEventListener sensorEventListener;
    private Listener listener;

    /**
     *
     * @param context Context of the activity
     */
    public Gyroscope(Context context){
        this.context = context;
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(listener != null){
                    listener.onTranslation(event.values[0], event.values[1], event.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void register(){
        manager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegister(){
        manager.unregisterListener(sensorEventListener);
    }
}
