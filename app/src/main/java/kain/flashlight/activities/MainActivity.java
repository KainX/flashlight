package kain.flashlight.activities;

import android.app.Activity;
import android.content.Context;
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
import kain.flashlight.utils.Gyroscope;
import kain.flashlight.utils.Manager;

/**
 * Flashlight app main activity
 * @author Kain
 */
public class MainActivity extends Activity {

    private Manager manager;
    private static int sdkVersion;
    private Gyroscope gyroscope;
    private boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscope = new Gyroscope(this);
        gyroscope.setListener((x,y,z)->{
            Log.d("APP-------->>>: ", String.valueOf(z));
            if(z > 5){
                if (isFlashOn)
                    flashOff();
                 else
                    flashOn();
            }
        });
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscope.unRegister(); //Unregister sensor listener to avoid drain the battery
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isFlashOn)
            flashOff();
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
        isFlashOn = true;
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
        isFlashOn = false;
    }

    public void setFlash(View v) {
        if(((Switch)v).isChecked())
            flashOn();
        else
            flashOff();
    }
}
