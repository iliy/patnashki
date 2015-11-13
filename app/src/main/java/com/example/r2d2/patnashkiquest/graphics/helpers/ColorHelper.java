package com.example.r2d2.patnashkiquest.graphics.helpers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by r2d2 on 08.11.15.
 */
public class ColorHelper {
    public static FloatBuffer getFloatBufferDefaultColor(int vertexLength){
        FloatBuffer colorBuffer = ByteBuffer.allocateDirect(vertexLength * 4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for(int i = 0; i < vertexLength; i++){
            colorBuffer.put(1f);
            colorBuffer.put(1f);
            colorBuffer.put(1f);
            colorBuffer.put(1f);
        }
        return colorBuffer;
    }
}
