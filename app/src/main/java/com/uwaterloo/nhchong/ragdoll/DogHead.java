package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class DogHead extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    DogHead(float _width, float _height) {
        super("Head", 60, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawRoundRect(rectangle, 50, 50, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);
        BodyPart parent = getParent();
        float headHeightBuffer = getHeight();
        float headWidthBuffer = parent.getWidth();

        anchorPointX = parent.getWidth();
        anchorPointY = 0;

        this.translate(headWidthBuffer, -headHeightBuffer);
    }
}
