package com.github.mccxj.igo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private int iX = 20;
    private int iY = 20;
    private int s = 20;
    private int radius = 3;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        iX = (getWidth() - 18 * s) / 2;
        for (int i = 0; i < 19; i++) {
            canvas.drawLine(iX, iY + s * i, iX + s * 18, iY + s * i, paint);
            canvas.drawLine(iX + s * i, iY, iX + s * i, iY + s * 18, paint);
        }
        
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(iX + s * 3, iY + s * 3, radius, paint);
        canvas.drawCircle(iX + s * 3, iY + s * 15, radius, paint);
        canvas.drawCircle(iX + s * 15, iY + s * 3, radius, paint);
        canvas.drawCircle(iX + s * 15, iY + s * 15, radius, paint);
        canvas.drawCircle(iX + s * 9, iY + s * 9, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(iX + s * 4, iY + s * 9, s/2, paint);
        
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(iX + s * 8, iY + s * 9, s/2, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(iX + s * 8, iY + s * 9, s/2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
