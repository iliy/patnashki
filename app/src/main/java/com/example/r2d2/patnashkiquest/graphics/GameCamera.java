package com.example.r2d2.patnashkiquest.graphics;


import android.opengl.Matrix;
import android.util.Log;

import com.example.r2d2.patnashkiquest.graphics.model.Vector3;

/**
 * Created by r2d2 on 11.11.15.
 */
public class GameCamera {
    public static final String TAG = GameCamera.class.getSimpleName();

    private float[] _viewMatrix = new float[16];

    private Vector3 _eyeC;
    private Vector3 _lookC;
    private Vector3 _upC;

    private static GameCamera _instance;

    private float _angle = 7f * (float)Math.PI / 4f;
    private float _angleStep = (float)Math.PI / 12f;

    public static GameCamera getInstace(){
        if(_instance == null){
            _instance = new GameCamera(3f, 3f, -3f, -3f, -2f, 3f, 0.0f, 0.5f, 0.0f);
        }
        return _instance;
    }

    private GameCamera(float eyeX, float eyeY, float eyeZ,
                       float lookX, float lookY, float lookZ,
                       float upX, float upY, float upZ){
        _eyeC = new Vector3(eyeX, eyeY, eyeZ);
        _lookC = new Vector3(lookX, lookY, lookZ);
        _upC = new Vector3(upX, upY, upZ);
    }

    public float[] getViewMatrix(){
        return _viewMatrix;
    }

    public void setEyeCoordinate(float x, float y, float z){
        _eyeC.X = x;
        _eyeC.Y = y;
        _eyeC.Z = z;
    }

    public void setLookCoordinate(float x, float y, float z){
        _lookC.X = x;
        _lookC.Y = y;
        _lookC.Z = z;
    }

    public void setUpCoordinate(float x, float y, float z){
        _upC.X = x;
        _upC.Y = y;
        _upC.Z = z;
    }

    public Vector3 getEyeCoordinate(){
        return _eyeC;
    }

    public Vector3 getLookCoordinate(){
        return _lookC;
    }

    public Vector3 getUpCoordinate(){
        return _upC;
    }


    public void goLeft(){
        _angle -= _angleStep;
        _eyeC.X = 4 * (float)Math.cos(_angle);
        _eyeC.Z = 4 * (float)Math.sin(_angle);
        _lookC.X = -_eyeC.X;
        _lookC.Z = -_eyeC.Z;
    }

    public void goRight(){
        _angle += _angleStep;
        _eyeC.X = 4 * (float)Math.cos(_angle);
        _eyeC.Z = 4 * (float)Math.sin(_angle);
        _lookC.X = -_eyeC.X;
        _lookC.Z = -_eyeC.Z;
        Log.d(TAG, "Angle: " + _angle + "; X: " + _eyeC.X + "; Z: " + _eyeC.Z);
    }

    public void goTop(){
        _eyeC.X = 0;
        _eyeC.Y = 5f;
        _eyeC.Z = 0;
        _lookC.X = 0;
        _lookC.Y = -5f;
        _lookC.Z = 0;
        _upC.X = 0.5f;
        _upC.Y = 0.0f;
        _upC.Z = 0.0f;
    }

    public void goBottom(){
        //3f, 3f, -3f, -3f, -2f, 3f
        _eyeC.X = 3f;
        _eyeC.Y = 3f;
        _eyeC.Z = -3f;
        _lookC.X = -3f;
        _lookC.Y = -2f;
        _lookC.Z = 3f;

        _upC.X = 0.0f;
        _upC.Y = 0.5f;
        _upC.Z = 0.0f;
    }

    public void make(){
        Matrix.setLookAtM(_viewMatrix, 0, _eyeC.X, _eyeC.Y, _eyeC.Z,
                                          _lookC.X, _lookC.Y, _lookC.Z,
                                          _upC.X, _upC.Y, _upC.Z);
    }
}
