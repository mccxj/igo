package com.github.mccxj.igo.view;

import static com.github.mccxj.go.GameConstants.BLACK;
import static com.github.mccxj.go.GameConstants.SIZE;
import static com.github.mccxj.go.GameConstants.WHITE;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.github.mccxj.go.GoGame;
import com.github.mccxj.go.sgf.SGFGame;

public class GameView extends View {
    private Paint paint = new Paint();
    public GoGame goGame;
    private int iX = 20;
    private int iY = 20;
    private int s = -1;
    private int radius = 3;

    private GestureDetector gd;
    private ScaleGestureDetector sgd;

    public GameView(Context context) {
        super(context);

        goGame = new GoGame();
        paint.setAntiAlias(true);
        gd = new GestureDetector(context, new GameGestureListener());
        sgd = new ScaleGestureDetector(context, new GameScaleGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (s == -1) {
            s = getWidth() / 20;
            iX = (getWidth() - 18 * s) / 2;
        }

        setBackgroundColor(Color.YELLOW);
        paint.setColor(Color.BLACK);

        canvas.translate(iX, iY);
        for (int i = 0; i < 19; i++) {
            canvas.drawLine(0, s * i, s * 18, s * i, paint);
            canvas.drawLine(s * i, 0, s * i, s * 18, paint);
        }

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(s * 3, s * 3, radius, paint);
        canvas.drawCircle(s * 3, s * 15, radius, paint);
        canvas.drawCircle(s * 15, s * 3, radius, paint);
        canvas.drawCircle(s * 15, s * 15, radius, paint);
        canvas.drawCircle(s * 9, s * 9, radius, paint);

        int[][] stones = goGame.getStones();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (BLACK == stones[i][j]) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.BLACK);
                    canvas.drawCircle(s * i, s * j, s / 2, paint);
                } else if (WHITE == stones[i][j]) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(s * i, s * j, s / 2, paint);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    canvas.drawCircle(s * i, s * j, s / 2, paint);
                }
            }
        }
    }

    public void setSGF(SGFGame game) {
        goGame.setSGF(game);
        goGame.reset();
        this.postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getPointerCount();
        return gd.onTouchEvent(event);
    }

    // if (MotionEvent.ACTION_UP == event.getAction()) {
    // int posX = (int) Math.round((event.getX() - iX) / s);
    // int posY = (int) Math.round((event.getY() - iY) / s);
    // int result = goGame.next(posX, posY);
    // if (result != INVALID) {
    // if (goGame.addStone(posX, posY)) {
    // if (result == SUCC)
    // System.out.println("SUCC");
    // else if (result == FAIL)
    // System.out.println("FAIL");
    // else {
    // if (result >= 1000)
    // result -= 1000;
    // goGame.addStone(result / SIZE, result % SIZE);
    // }
    // postInvalidate();
    // }
    // }
    // }
    // return true;
    // }

    private class GameGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            iX -= distanceX;
            iY -= distanceY;
            Log.d("IGO", "distanceX: " + distanceX + ", distanceY: " + distanceY);
            return true;
        }
    }

    private class GameScaleGestureListener implements OnScaleGestureListener {
        private float beforeFactor;
        private float mPivotX;
        private float mPivotY;
        private View mVSouce;
        private boolean isFillAfter;

        public GameScaleGestureListener() {
            mVSouce = GameView.this;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            final float factor = detector.getScaleFactor();
            Animation animation = new ScaleAnimation(beforeFactor, factor, beforeFactor, factor, mPivotX, mPivotY);
            animation.setFillAfter(true);
            mVSouce.startAnimation(animation);
            beforeFactor = factor;
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            if (checkIsNull()) {
                return false;
            }
            beforeFactor = 1f;
            mPivotX = detector.getFocusX() - mVSouce.getLeft();
            mPivotY = mVSouce.getTop() + (mVSouce.getHeight() >> 1);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (checkIsNull()) {
                return;
            }
            final float factor = detector.getScaleFactor();
            final int nWidth = (int) (mVSouce.getWidth() * factor);
            final int nHeight = (int) mVSouce.getHeight();
            final int nLeft = (int) (mVSouce.getLeft() - ((nWidth - mVSouce.getWidth()) * (mPivotX / mVSouce.getWidth())));
            final int nTop = (int) mVSouce.getTop();
            if (isFillAfter) {
                mVSouce.layout(nLeft, nTop, nLeft + nWidth, nTop + nHeight);
            }
            // MUST BE CLEAR ANIMATION. OTHERWISE WILL BE FLICKER
            mVSouce.clearAnimation();
        }

        public boolean checkIsNull() {
            return mVSouce == null ? true : false;
        }

        /**
         * if parameter is true that keeping same scale when next scaling.
         * 
         * @param isFill
         */
        public void setFillAfter(boolean isFill) {
            isFillAfter = isFill;
        }
    }
}
