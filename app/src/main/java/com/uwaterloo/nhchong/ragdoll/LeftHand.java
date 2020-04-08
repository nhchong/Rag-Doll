package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class LeftHand extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    LeftHand(float _width, float _height) {
        super("Left Hand", 35,  _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawOval(rectangle, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);

        BodyPart parent = getParent();
        float lowerArmHeightBuffer = parent.getHeight();
        float lowerArmWidthBuffer = (parent.getWidth() - getWidth()) / 2;

        anchorPointX = parent.getWidth()/2;
        anchorPointY = lowerArmHeightBuffer;

        translate(lowerArmWidthBuffer, lowerArmHeightBuffer);
    }
}
