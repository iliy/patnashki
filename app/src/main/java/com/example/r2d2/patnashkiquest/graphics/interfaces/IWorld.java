package com.example.r2d2.patnashkiquest.graphics.interfaces;

/**
 * Created by r2d2 on 08.11.15.
 */
public interface IWorld {
    void cameraLookAt(float x, float y, float z);
    void cameraLookTo(float x, float y, float z);
    void draw();
    void resize(int width, int height);
    void addObject(IRenderObject object);
    boolean onTouchDown(float x, float y);
    boolean onTouchUp(float x, float y);
    boolean onTouchDragged(float xStart, float yStart, float xEnd, float yEnd);
    void setCameraAnimationListener(ICameraAnimationListener listener);
}
