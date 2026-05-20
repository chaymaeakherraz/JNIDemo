package com.example.anative;

public class NativeLib {

    // Used to load the 'anative' library on application startup.
    static {
        System.loadLibrary("anative");
    }

    /**
     * A native method that is implemented by the 'anative' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}