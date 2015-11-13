package com.example.r2d2.patnashkiquest.graphics.objects;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.r2d2.patnashkiquest.AppConfig;
import com.example.r2d2.patnashkiquest.R;
import com.example.r2d2.patnashkiquest.graphics.helpers.ColorHelper;
import com.example.r2d2.patnashkiquest.graphics.helpers.OBJParser;
import com.example.r2d2.patnashkiquest.graphics.helpers.TextureHelper;
import com.example.r2d2.patnashkiquest.graphics.interfaces.IRenderObject;
import com.example.r2d2.patnashkiquest.graphics.model.DefaultColor;
import com.example.r2d2.patnashkiquest.graphics.model.ParseModel;
import com.example.r2d2.patnashkiquest.graphics.model.Vector3;
import com.example.r2d2.patnashkiquest.graphics.programs.SimpleProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by r2d2 on 08.11.15.
 */
public class SimpleObject implements IRenderObject {
    public static final String TAG = SimpleObject.class.getSimpleName();

    private Vector3 _position;
    private Vector3 _parentPosition;
    private Context _context;

    private float _rX = 0.0f;
    private float _rY = 0.0f;
    private float _rZ = 0.0f;

    private float _prX;
    private float _prY;
    private float _prZ;

    private SimpleProgram _glProgram;

    private float[] _modelMatrix = new float[16];

    private final int _textureHandle;

    private FloatBuffer _PositionBuffer;
    private FloatBuffer _ColorBuffer;
    private FloatBuffer _TextureCoordinateBuffer;
    private FloatBuffer _NormalsBuffer;

    private float[] _mvpMatrix = new float[16];

    private final int _vertexCount;


    public SimpleObject(Context context) {
        this(context, context.getString(R.string.empty_object));
    }

    public SimpleObject(Context context, String objSource) {
        this(context, objSource, R.drawable.emptytexture);
    }

    public SimpleObject(Context context, String objSource, int textureID) {
        this(context, objSource, textureID, DefaultColor.NOT_COLOR);
    }

    public SimpleObject(Context context, String objSource, int textureID, DefaultColor color) {
        _glProgram = SimpleProgram.getInstance(context);
        _context = context;
        ParseModel model = OBJParser.parse(context, objSource);

        _textureHandle = TextureHelper.loadTexture(context, textureID);

        _PositionBuffer = model.PositionBuffer;
        _TextureCoordinateBuffer = model.TextureCoordinateBuffer;
        _NormalsBuffer = model.NormalsBuffer;

        _ColorBuffer = ColorHelper.getFloatBufferDefaultColor(model.VertexCount);

        _vertexCount = model.VertexCount;

        _position = new Vector3();
        _parentPosition = new Vector3();

        _rZ = 0;
        _rY = 0;
        _rX = 0;
    }

    public void rotationX(float r) {
        _rX = r;
    }

    public void rotationY(float r) {
        _rY = r;
    }

    public void rotationZ(float r) {
        _rZ = r;
    }

    @Override
    public float getRotationX(){return _rX;}

    @Override
    public float getRotationY(){return _rY;}

    @Override
    public float getRotationZ(){return _rZ;}


    @Override
    public void setRotationParentX(float r){
        _prX = r;
    }

    @Override
    public void setRotationParentZ(float r){
        _prZ = r;
    }

    @Override
    public void setRotationParentY(float r){
        _prY = r;
    }

    @Override
    public void setParentPosition(Vector3 position){
        _parentPosition = position;
    }

    @Override
    public Context getContext() {
        return _context;
    }

    @Override
    public Vector3 getPosition() {
        return _position;
    }

    @Override
    public void setPosition(Vector3 position) {
        _position = position;
    }

    @Override
    public void draw(float[] projectionMatrix, float[] viewMatrix){//}, float[] modelMatrix){
        // Получениие указателей
        final int mvpMatrixHandle = _glProgram.getMVPMatrixHandle();
        final int mvMatrixHandle = _glProgram.getMVMatrixHandle();
        final int textureUniformHandle = _glProgram.getTextureUniformHandle();
        final int positionHandle = _glProgram.getPositionHandle();
        final int colorHandle = _glProgram.getColorHandle();
        final int normalHandle = _glProgram.getNormalHandle();
        final int textureCoordinateHandle = _glProgram.getTextureCoordinateHandle();

        Matrix.setIdentityM(_modelMatrix, 0);
        Matrix.translateM(_modelMatrix, 0, _position.X + _parentPosition.X,
                                           _position.Y + _parentPosition.Y,
                                           _position.Z + _parentPosition.Z);

        if(_rY + _prY != 0.0f)
            Matrix.rotateM(_modelMatrix, 0, _rY + _prY, 0.0f, 1.0f, 0.0f);

        if(_rX + _prX != 0.0f)
            Matrix.rotateM(_modelMatrix, 0, _rX + _prX, 1.0f, 0.0f, 0.0f);

        if(_rZ + _prZ != 0.0f)
            Matrix.rotateM(_modelMatrix, 0, _rZ + _prZ, 0.0f, 0.0f, 1.0f);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _textureHandle);

        GLES20.glUniform1i(textureUniformHandle, 0);

        _PositionBuffer.position(0);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false,
                0, _PositionBuffer);

        GLES20.glEnableVertexAttribArray(positionHandle);

        _ColorBuffer.position(0);
        GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false,
                0, _ColorBuffer);

        GLES20.glEnableVertexAttribArray(colorHandle);

        _NormalsBuffer.position(0);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                0, _NormalsBuffer);

        GLES20.glEnableVertexAttribArray(normalHandle);

        _TextureCoordinateBuffer.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, _TextureCoordinateBuffer);

        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        Matrix.multiplyMM(_mvpMatrix, 0, viewMatrix, 0, _modelMatrix, 0);

        GLES20.glUniformMatrix4fv(mvMatrixHandle, 1, false, _mvpMatrix, 0);

        Matrix.multiplyMM(_mvpMatrix, 0, projectionMatrix, 0, _mvpMatrix, 0);

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, _mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, _vertexCount);
    }
}
