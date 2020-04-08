package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Trunk extends BodyPart {

    // Construct a torso with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn with the upper-left corner at the origin
    // Assumes: width and height are positive numbers
    Trunk(float _width, float _height) {
        super("Torso", 0, _width, _height);
    }


    // Draw using the current matrix
    protected void drawBodyPart(Canvas canvas, Paint paint) {
        Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
        green.setColor(Color.BLACK);
        canvas.drawRoundRect(rectangle, 20, 20, green);
    }

    protected void initialize() {
        rectangle = new RectF(0, 0, width, height);
        translate((1800/2) - (width/2),2271 - height - 500);
    }

    protected void onDrag(MotionEvent event1, MotionEvent event2, float distanceX,
                          float distanceY) {
        float x = event2.getX();
        float y = event2.getY();
        switch (interactionMode) {
            case IDLE:
                break;
            case DRAGGING:
                this.translate(-distanceX, -distanceY);
        }
        oldX = x;
        oldY = y;

    }

    // Scale by sx, sy
    // Appends to the current matrix, so operations are cumulative
    protected void onScale(float focusX, float focusY, float scaleFactor) {
        if (contains(focusX, focusY)) {
            Log.d("DEBUG", "SCALING = " + scaleFactor);
            scaleMatrix.postScale(scaleFactor, scaleFactor);
            matrix = new Matrix();
            matrix.postConcat(rotationMatrix);
            matrix.postConcat(scaleMatrix);
            translate((1800/2) - (width/2),2271/3 - (height/2));
        }
    }
}
