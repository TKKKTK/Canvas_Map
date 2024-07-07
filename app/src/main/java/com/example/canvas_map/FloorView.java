package com.example.canvas_map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FloorView extends View {
    private static final String TAG = "FloorView";

    private Paint mPaint;

    private Path mPath;

    private List<IEventListener> iEventListeners;

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iEventListeners = new ArrayList<>();
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.RED);

    }

    /**
     * 绘制三角形
     */
    public void drawTriangle(){
        mPath = new Path();
        // 绘制一个三角形
        mPath.moveTo(100, 100);
        mPath.lineTo(200, 200);
        mPath.lineTo(100, 200);
        mPath.close();
    }

    /**
     * 绘制一个矩形
     */
    public void drawRect(){
        mPath = new Path();
        RectF rectF = new RectF(100,100,400,400);
        mPath.addRect(rectF, Path.Direction.CW);
        mPath.close();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (iEventListeners.size() > 0){
            for (IEventListener eventListener: iEventListeners){
                eventListener.event();
            }
            iEventListeners.clear();
        }
        Log.d(TAG, "ViewTest ==> onSizeChanged: width ==> " + w + "; height ==> " + h + "; oldWidth ==> " + oldw + "; oldHeight ==> " + oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "ViewTest ==> onMeasure: ");
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        Log.d(TAG, "ViewTest ==> onDraw: ");
    }

    public void transfrom(){
        Log.d(TAG, "ViewTest ==> transfrom: width ==>" + getWidth() +"; height ==>" + getHeight());
        if (getWidth() == 0 || getHeight() == 0){
            iEventListeners.add(this::transfrom);
            return;
        }
    }

    public void showRelation(){
        Log.d(TAG, "ViewTest ==> showRelation: width ==>" + getWidth() +"; height ==>" + getHeight());
        if (getWidth() == 0 || getHeight() == 0){
            iEventListeners.add(this::showRelation);
            return;
        }
    }

}
