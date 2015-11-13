package com.example.r2d2.patnashkiquest.test;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.r2d2.patnashkiquest.R;
import com.example.r2d2.patnashkiquest.graphics.SimpleWorld;
import com.example.r2d2.patnashkiquest.graphics.model.Vector3;
import com.example.r2d2.patnashkiquest.graphics.objects.ObjectsGroup;
import com.example.r2d2.patnashkiquest.graphics.objects.SimpleObject;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by r2d2 on 08.11.15.
 */
public class TestRender implements GLSurfaceView.Renderer {


    private SimpleWorld _world;
    private Context _context;


    public TestRender(Context context){
        _context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to black.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Use culling to remove back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);



        // Enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        _world = SimpleWorld.getInstance(_context);
        SimpleObject object1 = new SimpleObject(_context, "objects/panellvl2.obj", R.drawable.testtexture);
        object1.setPosition(new Vector3(0.0f, 0.0f, 0.0f));

        SimpleObject object2 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object2.setPosition(new Vector3(0.0f, 0.0f, 1.5f));

        SimpleObject object3 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object3.setPosition(new Vector3(1.5f, 0.0f, 1.5f));

        SimpleObject object4 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object4.setPosition(new Vector3(1.5f, 0.0f, 0.0f));

        SimpleObject object5 = new SimpleObject(_context, "objects/panellvl2.obj", R.drawable.testtexture);
        object5.setPosition(new Vector3(1.5f, 0.0f, -1.5f));

        SimpleObject object6 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object6.setPosition(new Vector3(0f, 0.0f, -1.5f));

        SimpleObject object7 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object7.setPosition(new Vector3(-1.5f, 0.0f, -1.5f));

        SimpleObject object8 = new SimpleObject(_context, "objects/panellvl2.obj", R.drawable.testtexture);
        object8.setPosition(new Vector3(-1.5f, 0.0f, 0.0f));

        SimpleObject object9 = new SimpleObject(_context, "objects/testcube.obj", R.drawable.testtexture);
        object9.setPosition(new Vector3(-1.5f, 0.0f, 1.5f));

//
//        object2.rotationZ(45.0f);
//        object2.rotationX(45.0f);

        ObjectsGroup group1 = new ObjectsGroup(_context);

        group1.addChildren(object1);
        group1.addChildren(object2);
        group1.addChildren(object3);
        group1.addChildren(object4);
        group1.addChildren(object5);
        group1.addChildren(object6);
        group1.addChildren(object7);
        group1.addChildren(object8);
        group1.addChildren(object9);

       // group1.setPosition(new Vector3(0.0f, -3.0f, 0.0f));
//        group1.rotationX(20.0f);
      //  group1.rotationY(5f);
//        group1.addChildren(object2);

        _world.addObject(group1);
        _world.cameraLookAt(3f, 3f, -3f);
        _world.cameraLookTo(-3f, -2f, 3f);
    }

    private boolean _draggedStart = false;

    private float _oldX = -1;
    private float _oldY = -1;

    private final static float delta = 10;

    public boolean onTouchEvent(MotionEvent e){
        int action = e.getAction();

        switch (action){
            case MotionEvent.ACTION_MOVE:
                if((Math.abs(_oldX - e.getX()) > delta || Math.abs(_oldY - e.getY()) > delta) && _draggedStart){
                    _world.onTouchDragged(_oldX, _oldY, e.getX(), e.getY());
                }
                break;
            case MotionEvent.ACTION_DOWN:
                _oldX = e.getX();
                _oldY = e.getY();
                _draggedStart = true;
                _world.onTouchDown(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(_oldX - e.getX()) <= delta && Math.abs(_oldY - e.getY()) <= delta)
                    _world.onTouchUp(e.getX(), e.getY());

                _draggedStart = false;
                break;
        }
        return true;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        _world.resize(width, height);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        _world.draw();
    }
}
