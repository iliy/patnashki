package com.example.r2d2.patnashkiquest.graphics;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.r2d2.patnashkiquest.R;
import com.example.r2d2.patnashkiquest.graphics.helpers.TextureHelper;
import com.example.r2d2.patnashkiquest.graphics.interfaces.ICameraAnimationListener;
import com.example.r2d2.patnashkiquest.graphics.interfaces.IRenderObject;
import com.example.r2d2.patnashkiquest.graphics.interfaces.IWorld;
import com.example.r2d2.patnashkiquest.graphics.model.Vector3;
import com.example.r2d2.patnashkiquest.graphics.programs.SimpleProgram;

import java.util.ArrayList;

/**
 * Created by r2d2 on 08.11.15.
 */
public class SimpleWorld implements IWorld {
    public static final String TAG = SimpleWorld.class.getSimpleName();

    private static SimpleWorld _instance;

    private Context _context;

    private float[] _ProjectionMatrix = new float[16];
    private float[] _ViewMatrix = new float[16];

    private Vector3 _lookFrom;
    private Vector3 _lookTo;

    private SimpleProgram _glProgram;

    private boolean _cameraAnimate = false;

    private GameCamera _camera;

    private ArrayList<IRenderObject> _objects = new ArrayList<>();

    private ICameraAnimationListener _cameraAnimationListener;


    public static SimpleWorld getInstance(Context context){
        if(_instance == null) _instance = new SimpleWorld(context);
        return _instance;
    }


    private SimpleWorld(Context context){
        _context = context;
        _lookFrom = new Vector3(0f, 0f, 5.5f);
        _lookTo = new Vector3(0f, 0f, -5f);
        _glProgram = SimpleProgram.getInstance(context);
        _camera = GameCamera.getInstace();
    }

    @Override
    public void cameraLookAt(float x, float y, float z) {
        _lookFrom = new Vector3(x, y, z);
    }

    @Override
    public void cameraLookTo(float x, float y, float z) {
        _lookTo = new Vector3(x, y, z);
    }

    @Override
    public boolean onTouchDown(float x, float y){
        Log.d(TAG, "Touch DOWN: X:" + String.valueOf(x) + "; Y:" + String.valueOf(y));
        return false;
    }

    @Override
    public boolean onTouchUp(float x, float y){
        Log.d(TAG, "Touch UP: X:" + String.valueOf(x) + "; Y:" + String.valueOf(y));
        return false;
    }

    private boolean _animateLeft = false;

    @Override
    public boolean onTouchDragged(float xStart, float yStart, float xNow, float yNow){
        if(_cameraAnimate) return false;
        _cameraAnimate = true;

        _animateLeft = xStart > xNow;

//        float dx = Math.abs(xStart - xNow);
//        float dy = Math.abs(xStart - xNow);
//
//        if(dy > dx) {
//            if (yStart < yNow) _camera.goTop();
//            else if (yStart > yNow) _camera.goBottom();
//            return true;
//        }else if(dx > dy){
//            if(xStart > xNow){
//                _camera.goRight();
//            }else if(xStart < xNow){
//                _camera.goLeft();
//            }
//        }

        return true;
    }

    @Override
    public void setCameraAnimationListener(ICameraAnimationListener listener){
        _cameraAnimationListener = listener;
    }

    private void cameraAnimate(){
        if(_animateLeft)
            _camera.goLeft();
        else
            _camera.goRight();
    }


    private float _stepAnimation = 0.0f;

    @Override
    public void draw() {
        GTimer.renderStart();
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

//        _stepAnimation += GTimer.getDeltaTime();

        _glProgram.usingProgram();

        if(_cameraAnimate){
            if(_stepAnimation < 6){
                cameraAnimate();
               _stepAnimation ++;
            }else{
                _stepAnimation=0;
                _cameraAnimate = false;
            }
        }

        _camera.make();

        for(IRenderObject object:_objects){
            object.draw(_ProjectionMatrix, _camera.getViewMatrix());
        }

//        if(_stepAnimation > 0.1999f){
//            _stepAnimation = 0.0f;
//        }
    }



    @Override
    public void resize(int width, int height) {
// Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(_ProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void addObject(IRenderObject object) {
        _objects.add(object);
    }
}
