package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Tounge extends BodyPart {

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    Tounge(float _width, float _height) {
        super("Head", 0, _width, _height);
    }

    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        canvas.drawRoundRect(rectangle, 20, 20, paint);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);
        BodyPart parent = getParent();
        float headHeightBuffer = parent.getHeight()/2;
        float headWidthBuffer = parent.getWidth();

        anchorPointX = headWidthBuffer;
        anchorPointY = headHeightBuffer;

        this.translate(headWidthBuffer - 20, headHeightBuffer);
    }
}
