package com.example.r2d2.patnashkiquest.graphics;

import android.os.SystemClock;

/**
 * Created by r2d2 on 11.11.15.
 */
public final class GTimer {
    private static long _lastTime = -1;

    public static void renderStart(){
        _lastTime = SystemClock.currentThreadTimeMillis();// - _lastTime;
    }

    public static float getDeltaTime(){
        return (float)(SystemClock.currentThreadTimeMillis() - _lastTime) / 1000f;
    }
}
