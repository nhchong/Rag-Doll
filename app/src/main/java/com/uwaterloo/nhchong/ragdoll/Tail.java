package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Tail extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    Tail(float _width, float _height) {
        super("Left Upper Arm", Float.MAX_VALUE, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        anchorPointX = 0;
        anchorPointY = getHeight()/2;

        translate(-getWidth(), 0);
        this.matrix.postRotate(30, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(30, anchorPointX, anchorPointY);
    }

    protected float getRotationDegrees(float prevX, float prevY, float newX, float newY) {
        float[] transformedPoint = getTransformedPoints(prevX, prevY);
        double angle = Math.toDegrees(Math.atan(newY/newX) -  Math.atan(transformedPoint[1]/transformedPoint[0]));
        System.out.println("angle =  " + Math.toDegrees(angle));
        return -(float)angle;
    }
}
