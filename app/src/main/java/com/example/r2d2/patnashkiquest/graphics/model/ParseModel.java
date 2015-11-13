package com.example.r2d2.patnashkiquest.graphics.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by r2d2 on 08.11.15.
 */
public class ParseModel {
    public FloatBuffer PositionBuffer;
    public FloatBuffer TextureCoordinateBuffer;
    public FloatBuffer NormalsBuffer;
    public int VertexCount;

    private final int _floatSize = 4;

    public ParseModel(Vector<Integer> faces, Vector<Integer> vtPoints, Vector<Integer> vnPoints,
                      Vector<Float> v, Vector<Float> vt, Vector<Float> vn){
        VertexCount = faces.size();

        PositionBuffer = ByteBuffer.allocateDirect(VertexCount * 3 * _floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        TextureCoordinateBuffer = ByteBuffer.allocateDirect(VertexCount * 2 * _floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        NormalsBuffer = ByteBuffer.allocateDirect(VertexCount * 3 * _floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();

        for(int i = 0; i < vnPoints.size(); i++){
            float x = vn.get(vnPoints.get(i) * 3);
            float y = vn.get(vnPoints.get(i) * 3 + 1);
            float z = vn.get(vnPoints.get(i) * 3 + 2);
            NormalsBuffer.put(x);
            NormalsBuffer.put(y);
            NormalsBuffer.put(z);
        }

        for(int i = 0; i < faces.size(); i++){
            float x = v.get(faces.get(i) * 3);
            float y = v.get(faces.get(i) * 3 + 1);
            float z = v.get(faces.get(i) * 3 + 2);

            PositionBuffer.put(x);
            PositionBuffer.put(y);
            PositionBuffer.put(z);
        }

        for(int i = 0; i < faces.size(); i++){
            float x = vt.get(vtPoints.get(i) * 2);
            float y = -vt.get(vtPoints.get(i) * 2 + 1);

            TextureCoordinateBuffer.put(x);
            TextureCoordinateBuffer.put(y);
        }

        TextureCoordinateBuffer.position(0);
        PositionBuffer.position(0);
        NormalsBuffer.position(0);
    }
}
