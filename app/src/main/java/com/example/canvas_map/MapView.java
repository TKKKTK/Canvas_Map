package com.example.canvas_map;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {
    private static final String TAG = "MapView";

    private List<FloorView> floorViews;

    private int currentIndex = 0;

    public MapView(@NonNull Context context) {
        this(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        selectView();
    }

    private void initView(Context context){
        floorViews = new ArrayList<>();
        FloorView floorView1 = new FloorView(context);
        floorView1.drawTriangle();
        FloorView floorView2 = new FloorView(context);
        floorView2.drawRect();

        floorViews.add(floorView1);
        floorViews.add(floorView2);
    }

    public void selectView(){
       if (getChildCount() > 0){
           removeView(floorViews.get(currentIndex));
       }
       currentIndex ++;
       currentIndex = currentIndex == floorViews.size() ? 0 : currentIndex;
       addView(floorViews.get(currentIndex));
       FloorView floorView = floorViews.get(currentIndex);
       floorView.transfrom();
       floorView.showRelation();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "ViewTest ==> onSizeChanged: width ==> " + w + "; height ==> " + h + "; oldWidth ==> " + oldw + "; oldHeight ==> " + oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "ViewTest ==> onMeasure: width==> " + widthMeasureSpec + "; height ==> " + heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "ViewTest ==> onDraw:");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "ViewTest ==> onLayout: ChildCount ==> " + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(left,top,right,bottom);
        }
    }
}
