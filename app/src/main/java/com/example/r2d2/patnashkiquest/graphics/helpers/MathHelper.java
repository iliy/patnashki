package com.example.r2d2.patnashkiquest.graphics.helpers;

import com.example.r2d2.patnashkiquest.graphics.model.Vector3;

import java.util.Vector;

/**
 * Created by r2d2 on 08.11.15.
 */
public final class MathHelper {

    public static Vector<Integer> Triangulate(Vector<Integer> points){
        Vector<Integer> result = new Vector<>();

        final int size = points.size() - 1;

        for(int i = 1; i < size; i++){
            result.add(points.get(0));
            result.add(points.get(i));
            result.add(points.get(i + 1));
        }

        return result;
    }

    public static Vector3 sumVectors(Vector3 vec1, Vector3 vec2){
        return new Vector3(vec1.X + vec2.X, vec1.Y + vec2.Y, vec1.Z + vec2.Z);
    }

    public static float gradusToRadian(float angle){
        return angle * ((float)Math.PI / 180);
    }
}
