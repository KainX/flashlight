package kain.flashlight.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import kain.flashlight.R;
import kain.flashlight.utils.Manager;

/**
 * Flashlight app main activity
 * @author Kain
 */
class sensor extends TriggerEventListener{

    @Override
    public void onTrigger(TriggerEvent event) {
        Log.d("MOTION: ", event.values.toString());
    }
}

public class MainActivity extends Activity {

    private Manager manager;
    private static int sdkVersion;

    private  SensorManager sm;
    private  Sensor sigMotion;
    private  sensor listener = new sensor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sigMotion = sm.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.requestTriggerSensor(listener, sigMotion);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.cancelTriggerSensor(listener, sigMotion);
    }

    /**
     * Set the flash light on (setFlashOnDeprecated method is still untested)
     */
    private void flashOn(){
        manager = new Manager(this);
        sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion>Build.VERSION_CODES.LOLLIPOP){
            manager.setFlashOn();
        }
        else {
            manager.setFlashOnDeprecated();
        }
    }

    /**
     * Set the flash light off (setFlashOffDeprecated method is still untested)
     */
    private void flashOff(){
        if(sdkVersion>Build.VERSION_CODES.LOLLIPOP){
            manager.setFlashOff();
        }
        else {
            manager.setFlashOffDeprecated();
        }
    }

    public void setFlash(View v) {
        if(((Switch)v).isChecked())
            flashOn();
        else
            flashOff();
    }
}


