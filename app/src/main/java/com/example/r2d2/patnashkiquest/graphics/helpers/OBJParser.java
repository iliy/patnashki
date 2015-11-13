package com.example.r2d2.patnashkiquest.graphics.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.r2d2.patnashkiquest.graphics.model.ParseModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by r2d2 on 08.11.15.
 */
public class OBJParser {
    public static final String TAG = OBJParser.class.getSimpleName();

    public static ParseModel parse(Context context, String filePath){
        AssetManager assetManager = context.getAssets();

        Vector<Float> v = new Vector<>();
        Vector<Float> vn = new Vector<>();
        Vector<Float> vt = new Vector<>();

        Vector<Integer> faces = new Vector<>();
        Vector<Integer> vnPoints = new Vector<>();
        Vector<Integer> vtPoints = new Vector<>();

        try{
            InputStream file = assetManager.open(filePath);

            Scanner scanner = new Scanner(file);

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.startsWith("#")) continue;

                String[] tokens = line.split(" ");

                int size = tokens.length;

                if(line.startsWith("vn")){
                    for(int i = 1; i < size; i++)
                        vn.add(Float.valueOf(tokens[i]));
                }else if(line.startsWith("vt")){
                    for(int i = 1; i < size; i++)
                        vt.add(Float.valueOf(tokens[i]));
                }else if(line.startsWith("v")){
                    for(int i = 1; i < size; i++)
                        v.add(Float.valueOf(tokens[i]));
                }else if(line.startsWith("f")){
                    if(tokens[1].contains("/")){
                        if(size == 4){
                            for(int i = 1; i < size; i++){
                                String[] buffer = tokens[i].split("/");
                                Integer s = Integer.valueOf(buffer[0]);
                                s--;
                                faces.add(s);
                                s = buffer[1].isEmpty() ? 0 : Integer.valueOf(buffer[1]);
                                s--;
                                vtPoints.add(s);
                                if(buffer.length == 2) {
                                    vnPoints.add(0);
                                    continue;
                                }
                                s = Integer.valueOf(buffer[2]);
                                s--;
                                vnPoints.add(s);
                            }
                        }else{
                            Vector<Integer> tmpFaces = new Vector<>();
                            Vector<Integer> tmpVnPoints = new Vector<>();
                            Vector<Integer> tmpVtPoints = new Vector<>();

                            for(int i = 1; i < size; i++){
                                String[] buffer = tokens[i].split("/");
                                Integer s = Integer.valueOf(buffer[0]);
                                s--;
                                tmpFaces.add(s);
                                s = buffer[1].isEmpty() ? 0 : Integer.valueOf(buffer[1]);
                                s--;
                                tmpVtPoints.add(s);
                                if(buffer.length == 2) {
                                    vnPoints.add(0);
                                    continue;
                                }
                                s = Integer.valueOf(buffer[2]);
                                s--;
                                tmpVnPoints.add(s);
                            }

                            faces.addAll(MathHelper.Triangulate(tmpFaces));
                            vtPoints.addAll(MathHelper.Triangulate(tmpVtPoints));
                            vnPoints.addAll(MathHelper.Triangulate(tmpVnPoints));
                        }
                    }else{
                        if(size == 4){
                            for(int i = 1; i < size; i++){
                                Integer s = Integer.valueOf(tokens[i]);
                                s--;
                                faces.add(s);
                            }
                        }else{
                            Vector<Integer> tmpFaces = new Vector<>();

                            for(int i = 1; i < size; i++){
                                Integer s = Integer.valueOf(tokens[i]);
                                s--;
                                tmpFaces.add(s);
                            }

                            faces.addAll(MathHelper.Triangulate(tmpFaces));
                        }
                    }
                }
            }

        }catch (IOException e){
            Log.e(TAG, e.getMessage());
        }

        return new ParseModel(faces, vtPoints, vnPoints, v, vt, vn);
    }
}
