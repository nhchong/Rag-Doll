package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class RightTwig extends BodyPart {

    float currHeight;
    float maxHeight;

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    RightTwig(float _width, float _height) {
        super("Left Lower Leg", Float.MAX_VALUE, _width, _height);
        currHeight = _height;
        maxHeight = _height * (float)1.5;
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
        green.setColor(Color.GREEN);
        canvas.drawRoundRect(rectangle, 20, 20, green);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        BodyPart parent = getParent();

        anchorPointX = parent.getWidth()-(width/2);
        anchorPointY = height;

        translate(parent.getWidth()-width, 0);
        this.matrix.postRotate(30, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(30, anchorPointX, anchorPointY);
    }


    protected float getRotationDegrees(float prevX, float prevY, float newX, float newY) {
        float[] transformedPoint = getTransformedPoints(prevX, prevY);
        double angle = Math.toDegrees(Math.atan(newY/newX) -  Math.atan(transformedPoint[1]/transformedPoint[0]));
        System.out.println("angle =  " + Math.toDegrees(angle));
        return -(float)angle;
    }

    protected void onDrag(MotionEvent event1, MotionEvent event2, float distanceX,
                          float distanceY) {
        switch (interactionMode) {
            case IDLE:
                break;
            case DRAGGING:
                this.translate(-distanceX, -distanceY);
                //scale(1, 101/100);
        }
    }
}
