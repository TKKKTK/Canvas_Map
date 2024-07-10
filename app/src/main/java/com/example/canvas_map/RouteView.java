package com.example.canvas_map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RouteView extends View {
    private Paint routePaint;
    private Paint traveledPaint;
    private Paint arrowPaint;
    private Path routePath;
    private Path traveledPath;
    private List<PointF> points;
    private PointF currentPosition;

    public RouteView(Context context) {
        this(context,null);
    }

    public RouteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RouteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        routePaint = new Paint();
        routePaint.setColor(Color.BLUE);
        routePaint.setStyle(Paint.Style.STROKE);
        routePaint.setDither(true);
        routePaint.setAntiAlias(true);
        routePaint.setStrokeWidth(10);

        traveledPaint = new Paint();
        traveledPaint.setColor(Color.GRAY);
        traveledPaint.setStyle(Paint.Style.STROKE);
        traveledPaint.setDither(true);
        traveledPaint.setAntiAlias(true);
        traveledPaint.setStrokeWidth(10);

        arrowPaint = new Paint();
        arrowPaint.setColor(Color.RED);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setDither(true);
        arrowPaint.setAntiAlias(true);
        arrowPaint.setStrokeWidth(2);

        routePath = new Path();
        traveledPath = new Path();
        points = new ArrayList<>();
    }

    public void setPoints(List<PointF> points) {
        this.points = points;
        createRoutePath();
        invalidate();
    }

    public void updateCurrentPosition(PointF currentPosition) {
        this.currentPosition = currentPosition;
        createTraveledPath();
        invalidate();
    }

    private void createRoutePath() {
        routePath.reset();
        if (points.isEmpty()) return;

        routePath.moveTo(points.get(0).x, points.get(0).y);
        for (PointF point : points) {
            routePath.lineTo(point.x, point.y);
        }
    }

    private void createTraveledPath() {
        traveledPath.reset();
        if (points.isEmpty() || currentPosition == null) return;

        traveledPath.moveTo(points.get(0).x, points.get(0).y);
        for (PointF point : points) {
            if (point.x <= currentPosition.x && point.y <= currentPosition.y) {
                traveledPath.lineTo(point.x, point.y);
            } else {
                break;
            }
        }
    }

    private void drawArrows(Canvas canvas) {
        float arrowLength = 6;
        float arrowAngle = (float) Math.toRadians(45);
        float totalDistance = 0;
        float arrowInterval = 100; // 每隔100像素绘制一个箭头

        for (int i = 0; i < points.size() - 1; i++) {
            PointF start = points.get(i);
            PointF end = points.get(i + 1);
            float dx = end.x - start.x;
            float dy = end.y - start.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            totalDistance += distance;
            if (totalDistance >= arrowInterval) {
                float angle = (float) Math.atan2(dy, dx);

                float x1 = end.x - arrowLength * (float) Math.cos(angle - arrowAngle);
                float y1 = end.y - arrowLength * (float) Math.sin(angle - arrowAngle);
                float x2 = end.x - arrowLength * (float) Math.cos(angle + arrowAngle);
                float y2 = end.y - arrowLength * (float) Math.sin(angle + arrowAngle);

                Path arrowPath = new Path();
                arrowPath.moveTo(x1, y1);
                arrowPath.lineTo(end.x, end.y);
                arrowPath.lineTo(x2, y2);
                //arrowPath.close();

                canvas.drawPath(arrowPath, arrowPaint);
                totalDistance = 0;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(routePath, routePaint);
        canvas.drawPath(traveledPath, traveledPaint);
        drawArrows(canvas);
    }
}
