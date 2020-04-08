package com.uwaterloo.nhchong.ragdoll;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import java.lang.String;
import java.lang.Math;
import android.view.MotionEvent;

import java.util.Vector;

public abstract class BodyPart {

    /**
     * Tracks our current interaction mode after a mouse-down
     */
    protected enum InteractionMode {
        IDLE,
        DRAGGING,
        SCALING,
        ROTATING
    }

    protected float anchorPointX, anchorPointY, DEGREES_ALLOWED, oldX, oldY, currentWidth;
    protected float totalDegrees = 0;
    protected InteractionMode interactionMode = InteractionMode.IDLE;
    String name;
    boolean selected = false;
    RectF rectangle;                                          // each body part is drawn via rectangle
    protected float width, height;
    private BodyPart parent = null;                                  // Pointer to our parent
    protected Vector<BodyPart> children = new Vector<BodyPart>();      // Holds all of our children
    Matrix matrix = new Matrix(); // identity matrix                 // Our transformation matrix
    Matrix rotationMatrix = new Matrix();
    Matrix scaleMatrix = new Matrix();
    Matrix transformMatrix = new Matrix();

    public BodyPart(String _name, float _DEGREES_ALLOWED, float _width, float _height) {
        name = _name;
        DEGREES_ALLOWED = _DEGREES_ALLOWED;
        width = _width;
        height = _height;
    }

    public void addChild(BodyPart b) {
        children.add(b);
        b.setParent(this);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float w) {
        width = w;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float h) {
        height = h;
    }

    public BodyPart getParent() {
        return parent;
    }

    public Matrix getScaleMatrix() { return scaleMatrix; }

    public Matrix getLocalTransform() {
        Matrix localMatrix = new Matrix(matrix);
        return localMatrix;
    }

    private void setParent(BodyPart b) {
        this.parent = b;
    }

    // Translate by dx, dy
    // Appends to the current matrix, so operations are cumulative
    protected void translate(float dx, float dy) {
        matrix.postTranslate(dx, dy);
    }

    // Scale by sx, sy
    // Appends to the current matrix, so operations are cumulative
    protected void onScale(float focusX, float focusY, float scaleFactor) { }

    // Rotates around anchor point by degrees
    // Implemented by sub-classes
    protected void rotate(float prevX, float prevY, float newX, float newY) {
        float degrees = getRotationDegrees(prevX, prevY, newX, newY);
        if (Math.abs(totalDegrees + degrees) < DEGREES_ALLOWED){
            totalDegrees += degrees;
            matrix.postRotate(degrees, anchorPointX, anchorPointY);
            rotationMatrix.postRotate(degrees, anchorPointX, anchorPointY);
        }
    }

    protected float getRotationDegrees(float prevX, float prevY, float newX, float newY) {
        float[] transformedPoint = getTransformedPoints(prevX, prevY);
        double angle = Math.toDegrees(Math.atan(newY/newX) -  Math.atan(transformedPoint[1]/transformedPoint[0]));
        return (float)angle;
    }

    protected boolean contains(float x, float y) {
        Matrix fullTransform = getFullTransform();
        Matrix inverse = new Matrix();
        final float[] point = new float[]{x, y};
        boolean success = fullTransform.invert(inverse);
        if (success) {
            inverse.mapPoints(point);
            //Log.d("DEBUG", "x = " + point[0] + ", y = " + point[1]);
            //Log.d("DEBUG", name + ": top = " + rectangle.top + ", bottom = " + rectangle.bottom + ", left = " + rectangle.left + ", right = " + rectangle.right);
            return rectangle.contains(point[0], point[1]);
        } else {
            return false;
        }
    }

    protected float[] getTransformedPoints(float x, float y) {
        Matrix fullTransform = getFullTransform();
        Matrix inverse = new Matrix();
        final float[] point = new float[]{x, y};
        boolean success = fullTransform.invert(inverse);
        if (success) {
            inverse.mapPoints(point);
        }
        return point;
    }


    /**
     * Returns the full transform to this object from the root
     */
    private Matrix getFullTransform() {
        Matrix returnTransform = new Matrix();
        BodyPart currBodyPart = this;
        while (currBodyPart != null) {
            returnTransform.postConcat(currBodyPart.getLocalTransform());
            currBodyPart = currBodyPart.getParent();
        }
        return returnTransform;
    }

    /**
     * Draws the sprite. This method will call drawSprite after
     * the transform has been set up for this sprite.
     */
    void draw(Canvas canvas, Paint paint) {
        Matrix oldMatrix = canvas.getMatrix();

        Matrix currMatrix = canvas.getMatrix();
        currMatrix.postConcat(getFullTransform());
        canvas.setMatrix(currMatrix);

        //draw the BodyPart (found in subclasses)
        this.drawBodyPart(canvas, paint);

        //restore original transform
        canvas.setMatrix(oldMatrix);

        for (BodyPart child : children) {
            child.draw(canvas, paint);
        }
    }

    protected void onDown(float x, float y) {
        interactionMode = InteractionMode.DRAGGING;
        oldX = x;
        oldY = y;
    }

    protected void onDrag(MotionEvent event1, MotionEvent event2, float distanceX,
                          float distanceY) {
        float x = event2.getX();
        float y = event2.getY();
        float prevX = oldX;
        float prevY = oldY;
        float[] transformedPoint = getTransformedPoints(x, y);
        switch (interactionMode) {
            case IDLE:
                break;
            case DRAGGING:
                this.rotate(prevX, prevY, transformedPoint[0], transformedPoint[1]);
                //scale(1, 101/100);
        }
        oldX = x;
        oldY = y;

    }

    /**
     * The method that actually does the sprite drawing. This method
     * is called after the transform has been set up in the draw() method.
     * Sub-classes should override this method to perform the drawing.
     */
    protected abstract void drawBodyPart(Canvas canvas, Paint paint);

}
