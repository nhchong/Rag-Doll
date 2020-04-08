package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

public class DogFrontRight extends BodyPart {

    float currHeight;
    float maxHeight;

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    DogFrontRight(float _width, float _height) {
        super("Left Upper Leg", 90, _width, _height);
        currHeight = _height;
        maxHeight = _height * (float)1.5;
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        BodyPart parent = getParent();
        float torsoHeightBuffer = parent.getHeight();

        anchorPointX = parent.getWidth() - (getWidth()/2);
        anchorPointY = torsoHeightBuffer;

        translate(parent.getWidth() - getWidth(), torsoHeightBuffer);
        matrix.postRotate(15, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(15, anchorPointX, anchorPointY);
    }

}
