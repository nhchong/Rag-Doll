package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

public class DogLowerLeg extends BodyPart {

    float currHeight;
    float maxHeight;

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    DogLowerLeg(float _width, float _height) {
        super("Left Upper Leg", 45, _width, _height);
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
        float upperArmHeightBuffer = parent.getHeight();
        float uppperArmWidthBuffer = (parent.getWidth() - getWidth()) / 2;

        anchorPointX = parent.getWidth()/2;
        anchorPointY = upperArmHeightBuffer;

        translate(uppperArmWidthBuffer, upperArmHeightBuffer);
        matrix.postRotate(335, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(335, anchorPointX, anchorPointY);
    }

}
