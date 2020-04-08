package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RightUpperArm extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    RightUpperArm(float _width, float _height) {
        super("Right Upper Arm", Float.MAX_VALUE, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        BodyPart parent = getParent();
        float torsoBuffer = parent.getWidth();
        float rightArmBuffer = getWidth()/2;
        anchorPointX = torsoBuffer;
        anchorPointY = 0;

        translate(torsoBuffer - rightArmBuffer, 0);
        matrix.postRotate(345, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(345, anchorPointX, anchorPointY);

    }
}
