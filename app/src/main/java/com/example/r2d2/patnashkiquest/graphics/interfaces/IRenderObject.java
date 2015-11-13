package com.example.r2d2.patnashkiquest.graphics.interfaces;

import android.content.Context;

import com.example.r2d2.patnashkiquest.graphics.model.Vector3;

/**
 * Created by r2d2 on 08.11.15.
 */
public interface IRenderObject {
    void draw(float[] projectionMatrix, float[] viewMatrix);//, float[] modelMatrix);
    Vector3 getPosition();
    void setPosition(Vector3 position);
    Context getContext();
    void rotationX(float r);
    void rotationZ(float r);
    void rotationY(float r);
    float getRotationZ();
    float getRotationX();
    float getRotationY();
    void setRotationParentX(float r);
    void setRotationParentZ(float r);
    void setRotationParentY(float r);
    void setParentPosition(Vector3 position);
}
