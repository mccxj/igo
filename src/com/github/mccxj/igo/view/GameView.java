package com.github.mccxj.igo.view;

import static com.github.mccxj.go.GameConstants.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

import com.github.mccxj.go.GoGame;
import com.github.mccxj.go.sgf.SGFGame;

public class GameView extends View {
    private Paint paint = new Paint();
    public GoGame goGame;
    private int iX = 20;
    private int iY = 20;
    private int s = 20;
    private int radius = 3;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private Matrix tmpMatrix = new Matrix();;
    private Matrix savedMatrix = new Matrix();
    private PointF startPoint = new PointF();
    private PointF endPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oldDistance;
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
        paint.setColor(Color.BLACK);

        s = getWidth() / 20;
        iX = (getWidth() - 18 * s) / 2;
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setSGF(SGFGame game) {
        goGame.setSGF(game);
        goGame.reset();
        this.postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getPointerCount();
        if (pointCount == 1)
            return gd.onTouchEvent(event);
        else
            return sgd.onTouchEvent(event);
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

    private class GameGestureListener extends GestureDetector.SimpleOnGestureListener {}

    private class GameScaleGestureListener implements OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub

        }
    }
}
