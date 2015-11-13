package com.example.r2d2.patnashkiquest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.r2d2.patnashkiquest.test.TestGLSSurfaceView;
import com.example.r2d2.patnashkiquest.test.TestRender;
import com.example.r2d2.patnashkiquest.test.lesson.LessonRenderer;

public class MainActivity extends Activity {
    private TestGLSSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)
        {
            mGLSurfaceView = new TestGLSSurfaceView(this);
        }
        else
        {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }
        if(mGLSurfaceView != null)
            setContentView(mGLSurfaceView);
    }

    @Override
    protected void onResume()
    {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
