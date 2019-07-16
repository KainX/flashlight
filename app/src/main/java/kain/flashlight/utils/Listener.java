package kain.flashlight.utils;

/**
 * Listener interface for the observable class(Gyroscope)
 */
@FunctionalInterface
public interface Listener {

    void onTranslation(float x, float y, float z);
}
