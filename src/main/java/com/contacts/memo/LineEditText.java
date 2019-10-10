package com.contacts.memo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

public class LineEditText extends AppCompatEditText {

    private Paint mPaint;
    private Rect mRect;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mRect = new Rect();

        mPaint.setColor(0xFFFD077);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {

       int height = ((View) this.getParent()).getHeight(); //access the hole page height..

        int lineheight = getLineHeight();
        int numberoflines =  height/lineheight; //now acccording to page we get number of lines

        Paint paint = mPaint;
        Rect rect = mRect;

        int baseLine = getLineBounds(0,rect);

        for(int i=0;i<numberoflines;i++){ //here we create number of lines..
            canvas.drawLine(rect.left,baseLine+1,rect.right,baseLine+1,paint);
            baseLine += lineheight;
        }
        super.onDraw(canvas);
    }
}
