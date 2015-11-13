package com.example.r2d2.patnashkiquest.graphics.objects;

import android.content.Context;

import com.example.r2d2.patnashkiquest.graphics.helpers.MathHelper;
import com.example.r2d2.patnashkiquest.graphics.interfaces.IRenderObject;
import com.example.r2d2.patnashkiquest.graphics.model.Vector3;

import java.util.ArrayList;

/**
 * Created by r2d2 on 08.11.15.
 */
public class ObjectsGroup implements IRenderObject {
    public static final String TAG = ObjectsGroup.class.getSimpleName();

    private ArrayList<IRenderObject> _childrens;

    private Context _context;
    private Vector3 _position;
    private Vector3 _parentPosition;

    private float _rX;
    private float _rY;
    private float _rZ;

    private float _prX;
    private float _prY;
    private float _prZ;

    private int _currentIndex;

    public ObjectsGroup(Context context){
        _context = context;
        _currentIndex = -1;
        _rX = _prX = 0.0f;
        _rY = _prY = 0.0f;
        _rZ = _prZ = 0.0f;
        _position = new Vector3(0.0f, 0.0f, 0.0f);
        _parentPosition = new Vector3(0.0f, 0.0f, 0.0f);

        _childrens = new ArrayList<>();
    }

    @Override
    public void draw(float[] projectionMatrix, float[] viewMatrix) {
        for(IRenderObject children: _childrens){
            children.draw(projectionMatrix, viewMatrix);
        }
    }

    @Override
    public Vector3 getPosition() {
        return _position;
    }

    @Override
    public void setPosition(Vector3 position) {
        _position = position;
        for(IRenderObject children:_childrens){
            children.setParentPosition(MathHelper.sumVectors(_parentPosition, position));
        }
    }

    @Override
    public void setParentPosition(Vector3 position){
        _parentPosition = position;
    }

    public void addChildren(IRenderObject renderObject){
        renderObject.setParentPosition(MathHelper.sumVectors(_parentPosition, _position));
//        renderObject.setRotationParentZ(_rZ + _prZ);
//        renderObject.setRotationParentX(_rX + _prX);
//        renderObject.setRotationParentZ(_rY + _prY);
        _childrens.add(renderObject);
    }

    public void removeChildren(IRenderObject renderObject){
        _childrens.remove(renderObject);
    }

    public void removeChildren(int index){
        if(index >= 0 && index <= _childrens.size()){
            _childrens.remove(index);
        }
    }

    public IRenderObject getNextChildren(){
        _currentIndex++;
        if(_currentIndex >= _childrens.size()){
            _currentIndex = -1;
            return null;
        }
        return _childrens.get(_currentIndex);
    }

    @Override
    public Context getContext() {
        return _context;
    }

    @Override
    public void rotationX(float r) {
        float angle = r + _prX;
        float alpha = MathHelper.gradusToRadian(angle);
        for(IRenderObject children: _childrens){
            Vector3 oldPosition = children.getPosition();
            float y = oldPosition.Y;
            float z = oldPosition.Z;
            float c = (float)Math.sqrt(y * y + z * z);
            float ny = (float)Math.cos(alpha)*c;
            float nz = (float)Math.sin(alpha)*c;
            children.setPosition(MathHelper.sumVectors(oldPosition , new Vector3(0, ny, nz)));
            children.setRotationParentX(angle);
        }
    }

    @Override
    public void rotationZ(float r) {
        float angle = r + _prZ;
        float alpha = MathHelper.gradusToRadian(angle);
        for(IRenderObject children: _childrens){
            Vector3 oldPosition = children.getPosition();
            float x = oldPosition.X;
            float y = oldPosition.Y;
            float c = (float)Math.sqrt(x * x + y * y);
            float nx = (float)Math.cos(alpha)*c;
            float ny = (float)Math.sin(alpha)*c;
            children.setPosition(new Vector3(nx, ny, oldPosition.Z));
            children.setRotationParentZ(angle);
        }
    }

    @Override
    public void rotationY(float r) {
        float angle = r + _prY;
        float alpha = MathHelper.gradusToRadian(angle);
        for(IRenderObject children: _childrens){
            Vector3 oldPosition = children.getPosition();
            float x = oldPosition.X;
            float z = oldPosition.Z;
            float c = (float)Math.sqrt(x * x + z * z);
            float nx = (float)Math.cos(alpha) * c;
            float nz = (float)Math.sin(alpha) * c;
            children.setPosition(new Vector3(nx, oldPosition.Y, nz));
            children.setRotationParentY(angle);
        }
    }

    public float getRotationX(){
        return _rX;
    }

    public float getRotationY(){
        return _rY;
    }

    public float getRotationZ(){
        return _rZ;
    }

    public void setRotationParentX(float r){
        _prX = r;
        rotationX(r);
    }

    public void setRotationParentZ(float r){
        _prZ = r;
        rotationZ(r);
    }

    public void setRotationParentY(float r){
        _prY = r;
        rotationY(r);
    }

}
