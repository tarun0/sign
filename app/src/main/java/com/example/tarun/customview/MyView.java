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

    private String LOG_TAG;
    private Paint paint;
    private ArrayList<Float> x;
    private ArrayList<Float> y;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LOG_TAG = getClass().getSimpleName();
        paint = new Paint();
        paint.setColor(Color.parseColor("#CD5C5C")); //Some random color
        paint.setStrokeWidth(15);
        x = new ArrayList<>();
        y = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x.size()>2) {
            for (int i = 1; i<x.size(); i++) {
                if (x.get(i-1) == -1.0 || x.get(i) == -1) {
                    //do nothing
                }
                else
                    canvas.drawLine(x.get(i-1), y.get(i-1), x.get(i), y.get(i), paint );
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x.add(event.getX());
                y.add(event.getY());
                break;
            }
            case MotionEvent.ACTION_UP: {
                x.add((float) -1.0);
                y.add((float) -1.0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                x.add(event.getX());
                y.add(event.getY());
                invalidate();
                break;
            }
        }
        return true;
    }
}
