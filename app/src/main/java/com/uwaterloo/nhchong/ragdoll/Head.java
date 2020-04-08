package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Head extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    Head(float _width, float _height) {
        super("Head", 50, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);
        BodyPart parent = getParent();
        float headHeightBuffer = getHeight();
        float headWidthBuffer = (parent.getWidth()/2)-(getWidth()/2);

        anchorPointX = parent.getWidth()/2;
        anchorPointY = 0;

        this.translate(headWidthBuffer, -headHeightBuffer);
    }

    protected float getRotationDegrees(float prevX, float prevY, float newX, float newY) {
        float[] transformedPoint = getTransformedPoints(prevX, prevY);
        double angle = Math.toDegrees(Math.atan(newY/newX) -  Math.atan(transformedPoint[1]/transformedPoint[0]));
        System.out.println("angle =  " + Math.toDegrees(angle));
        return -(float)angle;
    }
}
