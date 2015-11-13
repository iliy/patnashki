package com.example.r2d2.patnashkiquest.test;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by r2d2 on 10.11.15.
 */
public class TestGLSSurfaceView extends GLSurfaceView {
    public static final String TAG = TestGLSSurfaceView.class.getSimpleName();

    private TestRender _renderer;

    public TestGLSSurfaceView(Context context) {
        super(context);
        _renderer = new TestRender(context);
        this.setEGLContextClientVersion(2);
        this.setRenderer(_renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        return _renderer.onTouchEvent(e);
    }

}
