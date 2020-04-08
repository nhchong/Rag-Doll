package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RightLowerArm extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    RightLowerArm(float _width, float _height) {
        super("Right Lower Arm", 135, _width, _height);
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
        matrix.postRotate(15, anchorPointX, anchorPointY);
        this.rotationMatrix.postRotate(15, anchorPointX, anchorPointY);
    }
}
