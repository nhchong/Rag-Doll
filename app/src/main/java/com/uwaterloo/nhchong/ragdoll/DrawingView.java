package com.uwaterloo.nhchong.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector;

import java.util.Vector;

public class DrawingView extends View {

    Paint red_brush;
    Torso torso;
    DogTorso dogTorso;
    Trunk trunk;
    BodyPart interactiveBP;
    private Vector<BodyPart> bodyParts = new Vector<BodyPart>();
    int design = 1;

    private GestureDetector mDetector;
    private ScaleGestureDetector scaleDetector;


    float defaultTorsoHeight = 650;
    float defaultTorsoWidth = 375;
    float defaultHeadHeight = 300;
    float defaultHeadWidth = 200;
    float defaultUpperArmHeight = 375;
    float defaultUpperArmWidth = 100;
    float defaultUpperLegHeight = 450;
    float defaultUpperLegWidth = 150;
    float defaultLowerArmHeight = 300;
    float defaultLowerArmWidth = 75;
    float defaultLowerLegHeight = 375;
    float defaultLowerLegWidth = 100;
    float defaultHandHeight = 100;
    float defaultHandWidth = 75;
    float defaultFootHeight = 200;
    float defaultFootWidth = 75;
    float defaultDogTorsoHeight = 375;
    float defaultDogTorsoWidth = 750;
    float defaultDogHeadHeight = 200;
    float defaultDogHeadWidth = 300;
    float defaultDogToungeHeight = 80;
    float defaultDogToungeWidth = 100;
    float defaultDogTailHeight = 75;
    float defaultDogTailWidth = 375;
    float defaultDogUpperLegHeight = 275;
    float defaultDogUpperLegWidth = 140;
    float defaultDogLowerLegHeight = 190;
    float defaultDogLowerLegWidth = 100;
    float defaultBranchWidth = 100;
    float defaultBranchHeight = 500;
    float defaultTrunkWidth = 300;
    float defaultTrunkHeight = 800;
    float defaultTwigWidth = 75;
    float defaultTwigHeight = 200;

    public DrawingView(Context context) {
        super(context);

        mDetector = new GestureDetector(context, new MyGestureListener());
        scaleDetector = new ScaleGestureDetector(context, new MyScaleGestureDetector());

        red_brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        red_brush.setColor(Color.RED);

        reset(1);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean retval = this.scaleDetector.onTouchEvent(event);
        this.mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // tell shapes to draw themselves
        if (design == 1) {
            torso.draw(canvas, red_brush);
        } else if (design == 2) {
            dogTorso.draw(canvas, red_brush);
        } else {
            trunk.draw(canvas, red_brush);
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            int count = event.getPointerCount();
            float x = event.getX();
            float y = event.getY();
            for (int i = bodyParts.size(); i-- > 0;) {
                if (bodyParts.get(i).contains(x, y)) {
                    interactiveBP = bodyParts.get(i);
                    interactiveBP.onDown(x, y);
                    break;
                }
            }
            invalidate();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                                float distanceY) {
            float x = event2.getX();
            float y = event2.getY();
            if (interactiveBP != null) {
                if (interactiveBP.contains(x, y)) {
                    interactiveBP.onDrag(event1, event2, distanceX, distanceY);
                    invalidate();
                } else {
                    interactiveBP = null;
                }
            }
            return true;
        }

    }

    class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        /**
         * This is the active focal point in terms of the viewport. Could be a local
         * variable but kept here to minimize per-frame allocations.
         */
        private float lastSpanX;
        private float lastSpanY;

        // Detects that new pointers are going down.
        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            lastSpanX = scaleGestureDetector.getCurrentSpanX();
            lastSpanY = scaleGestureDetector.getCurrentSpanY();
            invalidate();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float spanX = scaleGestureDetector.getCurrentSpanX();
            float spanY = scaleGestureDetector.getCurrentSpanY();

            float focusX = scaleGestureDetector.getFocusX();
            float focusY = scaleGestureDetector.getFocusY();

            for (BodyPart bodyPart : bodyParts) {
                bodyPart.onScale(focusX, focusY, scaleGestureDetector.getScaleFactor());
            }

            lastSpanX = spanX;
            lastSpanY = spanY;
            invalidate();
            return true;
        }

    }

    void reset(int i) {
        bodyParts = new Vector<BodyPart>();

        if (i == 1) {
            setNormal();
        } else if (i == 2) {
            setDog();
        } else {
            setTree();
        }

        invalidate();
    }

    void setNormal() {
        design = 1;
        // define shapes to be drawn
        torso = new Torso(defaultTorsoWidth, defaultTorsoHeight);
        torso.initialize();

        Head head = new Head(defaultHeadWidth, defaultHeadHeight);
        torso.addChild(head);
        head.initialize();

        LeftUpperArm leftUpperArm = new LeftUpperArm(defaultUpperArmWidth, defaultUpperArmHeight);
        torso.addChild(leftUpperArm);
        leftUpperArm.initialize();

        RightUpperArm rightUpperArm = new RightUpperArm(defaultUpperArmWidth, defaultUpperArmHeight);
        torso.addChild(rightUpperArm);
        rightUpperArm.initialize();

        LeftUpperLeg leftUpperLeg = new LeftUpperLeg(defaultUpperLegWidth, defaultUpperLegHeight);
        torso.addChild(leftUpperLeg);
        leftUpperLeg.initialize();

        RightUpperLeg rightUpperLeg = new RightUpperLeg(defaultUpperLegWidth, defaultUpperLegHeight);
        torso.addChild(rightUpperLeg);
        rightUpperLeg.initialize();

        LeftLowerArm leftLowerArm = new LeftLowerArm(defaultLowerArmWidth, defaultLowerArmHeight);
        leftUpperArm.addChild(leftLowerArm);
        leftLowerArm.initialize();

        RightLowerArm rightLowerArm = new RightLowerArm(defaultLowerArmWidth, defaultLowerArmHeight);
        rightUpperArm.addChild(rightLowerArm);
        rightLowerArm.initialize();

        LeftLowerLeg leftLowerLeg = new LeftLowerLeg(defaultLowerLegWidth, defaultLowerLegHeight);
        leftUpperLeg.addChild(leftLowerLeg);
        leftLowerLeg.initialize();

        RightLowerLeg rightLowerLeg = new RightLowerLeg(defaultLowerLegWidth, defaultLowerLegHeight);
        rightUpperLeg.addChild(rightLowerLeg);
        rightLowerLeg.initialize();

        LeftHand leftHand = new LeftHand(defaultHandWidth, defaultHandHeight);
        leftLowerArm.addChild(leftHand);
        leftHand.initialize();

        RightHand rightHand = new RightHand(defaultHandWidth, defaultHandHeight);
        rightLowerArm.addChild(rightHand);
        rightHand.initialize();

        LeftFoot leftFoot = new LeftFoot(defaultFootWidth, defaultFootHeight);
        leftLowerLeg.addChild(leftFoot);
        leftFoot.initialize();

        RightFoot rightFoot = new RightFoot(defaultFootWidth, defaultFootHeight);
        rightLowerLeg.addChild(rightFoot);
        rightFoot.initialize();

        bodyParts.add(torso);
        bodyParts.add(head);
        bodyParts.add(leftUpperArm);
        bodyParts.add(rightUpperArm);
        bodyParts.add(leftUpperLeg);
        bodyParts.add(rightUpperLeg);
        bodyParts.add(leftLowerArm);
        bodyParts.add(rightLowerArm);
        bodyParts.add(leftLowerLeg);
        bodyParts.add(rightLowerLeg);
        bodyParts.add(leftHand);
        bodyParts.add(rightHand);
        bodyParts.add(leftFoot);
        bodyParts.add(rightFoot);
    }

    void setDog() {
        design = 2;
        // define shapes to be drawn
        dogTorso = new DogTorso(defaultDogTorsoWidth, defaultDogTorsoHeight);
        dogTorso.initialize();

        DogHead dogHead = new DogHead(defaultDogHeadWidth, defaultDogHeadHeight);
        dogTorso.addChild(dogHead);
        dogHead.initialize();

        Tail tail = new Tail(defaultDogTailWidth, defaultDogTailHeight);
        dogTorso.addChild(tail);
        tail.initialize();

        DogBackLeft backLeft = new DogBackLeft(defaultDogUpperLegWidth,defaultDogUpperLegHeight);
        dogTorso.addChild(backLeft);
        backLeft.initialize();

        DogBackRight backRight = new DogBackRight(defaultDogUpperLegWidth,defaultDogUpperLegHeight);
        dogTorso.addChild(backRight);
        backRight.initialize();

        DogFrontLeft frontLeft = new DogFrontLeft(defaultDogUpperLegWidth,defaultDogUpperLegHeight);
        dogTorso.addChild(frontLeft);
        frontLeft.initialize();

        DogFrontRight frontRight = new DogFrontRight(defaultDogUpperLegWidth,defaultDogUpperLegHeight);
        dogTorso.addChild(frontRight);
        frontRight.initialize();

        DogLowerLeg backLowerLeft = new DogLowerLeg(defaultDogLowerLegWidth, defaultDogLowerLegHeight);
        backLeft.addChild(backLowerLeft);
        backLowerLeft.initialize();

        DogLowerLeg backLowerRight = new DogLowerLeg(defaultDogLowerLegWidth, defaultDogLowerLegHeight);
        backRight.addChild(backLowerRight);
        backLowerRight.initialize();

        DogLowerLeg frontLowerLeft = new DogLowerLeg(defaultDogLowerLegWidth, defaultDogLowerLegHeight);
        frontLeft.addChild(frontLowerLeft);
        frontLowerLeft.initialize();

        DogLowerLeg frontLowerRight = new DogLowerLeg(defaultDogLowerLegWidth, defaultDogLowerLegHeight);
        frontRight.addChild(frontLowerRight);
        frontLowerRight.initialize();

        Tounge tounge = new Tounge(defaultDogToungeWidth, defaultDogToungeHeight);
        dogHead.addChild(tounge);
        tounge.initialize();

        bodyParts.add(dogTorso);
        bodyParts.add(dogHead);
        bodyParts.add(tail);
        bodyParts.add(backLeft);
        bodyParts.add(backRight);
        bodyParts.add(frontLeft);
        bodyParts.add(frontRight);
        bodyParts.add(tounge);
        bodyParts.add(backLowerLeft);
        bodyParts.add(backLowerRight);
        bodyParts.add(frontLowerLeft);
        bodyParts.add(frontLowerRight);

    }

    void setTree() {
        design = 3;

        // define shapes to be drawn
        trunk = new Trunk(defaultTrunkWidth, defaultTrunkHeight);
        trunk.initialize();

        Branch branch1 = new Branch(defaultBranchWidth, defaultBranchHeight);
        trunk.addChild(branch1);
        branch1.initialize();

        LeftBranch branch2 = new LeftBranch(defaultBranchWidth, defaultBranchHeight);
        trunk.addChild(branch2);
        branch2.initialize();

        RightBranch branch3 = new RightBranch(defaultBranchWidth, defaultBranchHeight);
        trunk.addChild(branch3);
        branch3.initialize();

        LeftTwig clTwig = new LeftTwig(defaultTwigWidth, defaultTwigHeight);
        branch1.addChild(clTwig);
        clTwig.initialize();

        LeftTwig llTwig = new LeftTwig(defaultTwigWidth, defaultTwigHeight);
        branch2.addChild(llTwig);
        llTwig.initialize();

        LeftTwig rlTwig = new LeftTwig(defaultTwigWidth, defaultTwigHeight);
        branch3.addChild(rlTwig);
        rlTwig.initialize();

        RightTwig crTwig = new RightTwig(defaultTwigWidth, defaultTwigHeight);
        branch1.addChild(crTwig);
        crTwig.initialize();

        RightTwig lrTwig = new RightTwig(defaultTwigWidth, defaultTwigHeight);
        branch2.addChild(lrTwig);
        lrTwig.initialize();

        RightTwig rrTwig = new RightTwig(defaultTwigWidth, defaultTwigHeight);
        branch3.addChild(rrTwig);
        rrTwig.initialize();

        bodyParts.add(trunk);
        bodyParts.add(branch1);
        bodyParts.add(branch2);
        bodyParts.add(branch3);
        bodyParts.add(clTwig);
        bodyParts.add(llTwig);
        bodyParts.add(rlTwig);
        bodyParts.add(crTwig);
        bodyParts.add(lrTwig);
        bodyParts.add(rrTwig);


    }
}

