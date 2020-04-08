package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class RightUpperLeg extends BodyPart {

    float currHeight;
    float maxHeight;

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    RightUpperLeg(float _width, float _height) {
        super("Right Upper Leg", 90, _width, _height);
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
        float torsoWidthBuffer = parent.getWidth();
        float torsoHeightBuffer = parent.getHeight();

        anchorPointX = torsoWidthBuffer - getWidth()/2;
        anchorPointY = torsoHeightBuffer;

        translate(torsoWidthBuffer - getWidth(), torsoHeightBuffer);
    }

    // Scale by sx, sy
    // Appends to the current matrix, so operations are cumulative
    protected void onScale(float focusX, float focusY, float scaleFactor) {
        if (contains(focusX, focusY)) {
            Log.d("DEBUG", "SCALING = " + scaleFactor);
            scaleMatrix.postScale(1, scaleFactor);
            matrix = new Matrix();
            matrix.postConcat(scaleMatrix);
            matrix.preConcat(rotationMatrix);
            BodyPart parent = getParent();
            float torsoWidthBuffer = parent.getWidth();
            float torsoHeightBuffer = parent.getHeight();
            translate(torsoWidthBuffer - getWidth(), torsoHeightBuffer);
        }
    }
}
