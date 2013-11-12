package com.github.mccxj.igo.view;

import static com.github.mccxj.go.GameConstants.BLACK;
import static com.github.mccxj.go.GameConstants.SIZE;
import static com.github.mccxj.go.GameConstants.WHITE;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.github.mccxj.go.GoGame;
import com.github.mccxj.go.sgf.SGFGame;

public class GameView extends View {
    public GoGame goGame;
    private int iX = 20;
    private int iY = 20;
    private int s = 20;
    private int radius = 3;

    public GameView(Context context) {
        super(context);
        goGame = new GoGame();
    }

    public GameView(Context context, AttributeSet attr) {
        super(context, attr);
        goGame = new GoGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
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
}
