package com.example.tarun.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tarun on 23-06-2017.
 */

public class MyView extends View {

    private Path path;
    private String LOG_TAG;
    private Paint paint;
    private float mCurrentX = -1;
    private float mCurrentY = -1;
    private Canvas mCanvas;
    private float eX;
    private float eY;
    private ArrayList<Float> x;
    private ArrayList<Float> y;
    private ArrayList<Path> p;
    private ArrayList<Integer> t;
    Coordinates c;
    Context co;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LOG_TAG = getClass().getSimpleName();
        paint = new Paint();
        paint.setColor(Color.parseColor("#CD5C5C")); //Some random color
        paint.setStrokeWidth(15);
        mCanvas = new Canvas();
        x = new ArrayList<>();
        y = new ArrayList<>();
        p = new ArrayList<>();
        t = new ArrayList<>();
        path = new Path();
        co = context;

        c = new Coordinates();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mCanvas = canvas;
        //Toast.makeText(getContext(), "onDraw", Toast.LENGTH_SHORT).show();
        //Log.e(LOG_TAG, "onDraw");
        if (x.size()>2) {
            for (int i = 1; i<x.size(); i++) {
                //if (t.get(i-1) != 1)
                //canvas.drawLine(x.get(i-1), y.get(i-1), x.get(i), y.get(i), paint );
                if (!(c.getTemp().get(i) == 0 && c.getTemp().get(i-1) == 0)) {
                    canvas.drawLine(c.getXc().get(i),
                            c.getYc().get(i),
                            c.getXc().get(i-1),
                            c.getYc().get(i-1),
                            paint);
                } //else Log.e("no line", "!");
                    //Toast.makeText(co, "No line", Toast.LENGTH_SHORT).show();

            }
        }
        //for (Path thispath: p) {
            //canvas.drawPath(path, paint);
        //}
        //canvas.drawLine(mCurrentX, mCurrentY, eX, eY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                    mCurrentX = event.getX();
                    mCurrentY = event.getY();
                x.add(mCurrentX);
                y.add(mCurrentY);
                t.add(0);
                c.addT(0);
                c.addX(event.getX());
                c.addY(event.getY());
                //Log.e(LOG_TAG, " - DOWN -");
                //Log.e(LOG_TAG, " getX: " + event.getX());

                //Path ip = new Path();
                //path.moveTo(event.getX(), event.getY());
                //p.add(path);
                break;
            }
            case MotionEvent.ACTION_UP: {
                //Log.e(LOG_TAG, " - UP -");
                //Log.e(LOG_TAG, " getX: " + event.getX());
                /*mCurrentX = -1;
                mCurrentY = -1;*/
                eX = event.getX();
                eY = event.getY();
                t.add(1);
                c.addT(1);
                c.addX(event.getX());
                c.addY(event.getY());
                //Path ip = new Path();
                //path.lineTo(event.getX(), event.getY());
                //p.add(ip);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //mCanvas.drawLine(mCurrentX, mCurrentY, event.getX(), event.getY(), paint);
                /*mCurrentX = event.getX();
                mCurrentY = event.getY();
                eX = event.getX();
                eY = event.getY();*/
                x.add(event.getX());
                y.add(event.getY());
                t.add(0);
                c.addT(1);
                c.addX(event.getX());
                c.addY(event.getY());
                //Log.e(LOG_TAG, " - MOVE -");
                //Log.e(LOG_TAG, " getX: " + event.getX());
                invalidate();
                break;
            }
        }
        return true;
    }

    private class Coordinates {
        ArrayList<Float> xc;
        ArrayList<Float> yc;
        ArrayList<Integer> temp;

        Coordinates() {
            xc = new ArrayList<>();
            yc = new ArrayList<>();
            temp = new ArrayList<>();
        }

        void addX(Float p) {
            xc.add(p);
        }

        void addY(Float p) {
            yc.add(p);
        }

        void addT(int i) {
            temp.add(i);
        }

        ArrayList<Float> getXc() {
            return xc;
        }

        ArrayList<Float> getYc() {
            return yc;
        }

        ArrayList<Integer> getTemp() {
            return temp;
        }
    }
}
