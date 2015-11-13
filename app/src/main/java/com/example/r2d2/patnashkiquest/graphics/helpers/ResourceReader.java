package com.example.r2d2.patnashkiquest.graphics.helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by r2d2 on 08.11.15.
 */
public class ResourceReader {
    public static final String TAG = ResourceReader.class.getSimpleName();

    public static String readTextFileFromRawResource(final Context context,
                                                     final int resourceId)
    {
        final InputStream inputStream = context.getResources().openRawResource(
                resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();

        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append('\n');
            }
        }
        catch (IOException e)
        {
            return null;
        }

        return body.toString();
    }

    public static String readTextFromAssets(Context context, String fileName){
        final StringBuilder body = new StringBuilder();
        try{
            InputStream file = context.getAssets().open(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                body.append(scanner.nextLine());
                body.append("\n");
            }
        }catch (IOException e){
            Log.e(TAG, e.getMessage());
        }
        return body.toString();
    }
}
