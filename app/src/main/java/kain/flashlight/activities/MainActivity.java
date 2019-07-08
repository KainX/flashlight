package kain.flashlight.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import kain.flashlight.R;
import kain.flashlight.utils.Manager;

public class MainActivity extends Activity {

    private Manager manager;
    private int sdkVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        manager = new Manager(this);
        sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion>Build.VERSION_CODES.LOLLIPOP){
            manager.setFlashOn();
        }
        else {
            manager.setFlashOnDeprecated();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sdkVersion>Build.VERSION_CODES.LOLLIPOP){
            manager.setFlashOff();
        }
        else {
            manager.setFlashOffDeprecated();
        }
    }
}
