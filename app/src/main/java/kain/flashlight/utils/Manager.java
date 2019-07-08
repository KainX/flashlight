package kain.flashlight.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import static android.content.Context.CAMERA_SERVICE;

public final class Manager {
    private Context context;
    private CameraManager cm;
    private Camera camera;

    public Manager(Context context){
        this.context = context;
        cm = (CameraManager) context.getSystemService(CAMERA_SERVICE);
    }

    public String getFlashDeviceID(){
        String[] devices = {};
        try{
            devices = cm.getCameraIdList();
            for(String device: devices){
                if(cm.getCameraCharacteristics(device).get(CameraCharacteristics.FLASH_INFO_AVAILABLE))
                    return device;
            }
        }catch(CameraAccessException e){
            e.printStackTrace();
        }
        return "No flash devices found";
    }

    @TargetApi(23)
    public void setFlashOn(){
        try {
            cm.setTorchMode(getFlashDeviceID(), true);
        }catch (CameraAccessException e){

        }
    }

    @TargetApi(23)
    public void setFlashOff(){
        try {
            cm.setTorchMode(getFlashDeviceID(), false);
        }catch (CameraAccessException e){

        }
    }

    @TargetApi(21)
    public void setFlashOnDeprecated(){
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    @TargetApi(21)
    public void setFlashOffDeprecated(){
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }
}
