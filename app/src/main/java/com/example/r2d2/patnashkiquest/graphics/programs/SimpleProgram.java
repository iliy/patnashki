package com.example.r2d2.patnashkiquest.graphics.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.r2d2.patnashkiquest.graphics.helpers.ResourceReader;
import com.example.r2d2.patnashkiquest.graphics.helpers.ShaderHelper;
import com.example.r2d2.patnashkiquest.graphics.interfaces.IShaderProgram;

/**
 * Created by r2d2 on 08.11.15.
 */
public class SimpleProgram implements IShaderProgram{
    public static final String TAG = SimpleProgram.class.getSimpleName();

    private final int _programHandle;

    private static SimpleProgram _instance;

    public static SimpleProgram getInstance(Context context){
        if(_instance == null){
            _instance = new SimpleProgram(context);
        }

        return _instance;
    }

    private SimpleProgram(Context context){
        String vertexShaderSource = ResourceReader.readTextFromAssets(context, "shaders/per_pixel_vertex_shader.glsh");
        String fragmentShaderSource = ResourceReader.readTextFromAssets(context, "shaders/per_pixel_fragment_shader.glsh");

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);

        _programHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{
                "a_Position",  "a_Color", "a_Normal", "a_TexCoordinate"
        });
    }

    public int getMVPMatrixHandle(){
        return GLES20.glGetUniformLocation(_programHandle, "u_MVPMatrix");
    }

    public int getMVMatrixHandle(){
        return GLES20.glGetUniformLocation(_programHandle, "u_MVMatrix");
    }

    public int getTextureUniformHandle(){
        return GLES20.glGetUniformLocation(_programHandle, "u_Texture");
    }

    public int getPositionHandle(){
        return GLES20.glGetAttribLocation(_programHandle, "a_Position");
    }

    public int getColorHandle(){
        return GLES20.glGetAttribLocation(_programHandle, "a_Color");
    }

    public int getTextureCoordinateHandle(){
        return GLES20.glGetAttribLocation(_programHandle, "a_TexCoordinate");
    }

    public int getNormalHandle(){
        return GLES20.glGetAttribLocation(_programHandle, "a_Normal");
    }

    @Override
    public void usingProgram(){
        GLES20.glUseProgram(_programHandle);
    }

    @Override
    public int getHandle() {
        return _programHandle;
    }
}
