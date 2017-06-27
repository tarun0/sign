package com.example.tarun.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Tarun on 23-06-2017.
 */

public class SignatureView extends View {

    private String LOG_TAG;
    private Paint signPaint;
    private ArrayList<Float> mXCoordinateList;
    private ArrayList<Float> mYCoordinateList;
    private File signatureFile;
    private OnSignatureSaveListener mOnSignatureSaveListener;
    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LOG_TAG = getClass().getSimpleName();

        setDrawingCacheEnabled(true);
        Resources r = getResources();
        int strokeSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());

        signPaint = new Paint();
        signPaint.setColor(Color.BLACK);
        signPaint.setStrokeWidth(strokeSize);
        signPaint.setAntiAlias(true);
        mXCoordinateList = new ArrayList<>();
        mYCoordinateList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (mXCoordinateList.size()>2) {
            for (int i = 1; i<mXCoordinateList.size(); i++) {
                if (!(mXCoordinateList.get(i-1) == -1.0 || mXCoordinateList.get(i) == -1)) {
                    canvas.drawLine(mXCoordinateList.get(i-1), mYCoordinateList.get(i-1),
                            mXCoordinateList.get(i), mYCoordinateList.get(i), signPaint );
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mXCoordinateList.add(event.getX());
                mYCoordinateList.add(event.getY());
                break;
            }
            case MotionEvent.ACTION_UP: {
                mXCoordinateList.add((float) -1.0);
                mYCoordinateList.add((float) -1.0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mXCoordinateList.add(event.getX());
                mYCoordinateList.add(event.getY());
                invalidate();
                break;
            }
        }
        return true;
    }

    interface OnSignatureSaveListener {
        void onError(String errorMsg);
        void onSuccess(String filePath);
    }

    public void setSignatureSaveListener(OnSignatureSaveListener listener) {
     mOnSignatureSaveListener = listener;
    }

    public void saveSignature() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Signatures/";
        File signatureStorageDirectory = new File(path);
              if (!signatureStorageDirectory.exists()) {
                   if (signatureStorageDirectory.mkdir()) {
                        return;
                   }
                   else mOnSignatureSaveListener.onError("Couldn't create directory!");
             }

        try {
            Bitmap signatureBitmap = getDrawingCache();
            signatureFile = new File(signatureStorageDirectory.getPath() + "sign" + ".jpeg");
            signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(signatureFile));
        } catch (FileNotFoundException e) {
            mOnSignatureSaveListener.onError(e.getMessage());
        }
        mOnSignatureSaveListener.onSuccess(signatureFile.getAbsolutePath());
    }

    public void clear() {
        mXCoordinateList.clear();
        mYCoordinateList.clear();
        destroyDrawingCache();
        invalidate();
    }
}
