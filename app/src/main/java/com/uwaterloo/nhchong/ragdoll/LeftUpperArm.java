package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class LeftUpperArm extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    LeftUpperArm(float _width, float _height) {
        super("Left Upper Arm", Float.MAX_VALUE, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        anchorPointX = 0;
        anchorPointY = 0;

        this.matrix.postRotate(15, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(15, anchorPointX, anchorPointY);
        translate(-getWidth()/2, 0);
    }
}
