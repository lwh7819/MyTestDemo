package com.cd.mytestdemo.excem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class BetterHighlightSpan extends ReplacementSpan {
  
    private int backgroundColor;  
    public BetterHighlightSpan(int backgroundColor) {  
        super();  
        this.backgroundColor = backgroundColor;  
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        return Math.round(paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     Paint paint) {
  
        // save current color  
        int oldColor = paint.getColor();  
  
        // calculate new bottom position considering the fontSpacing  
        float fontSpacing = paint.getFontSpacing();  
        float newBottom = bottom - fontSpacing;  
  
        // change color and draw background highlight  
        RectF rect = new RectF(x, top, x + paint.measureText(text, start, end), newBottom);
        paint.setColor(backgroundColor);  
        canvas.drawRect(rect, paint);  
  
        // revert color and draw text  
        paint.setColor(oldColor);  
        canvas.drawText(text, start, end, x, y, paint);  
    }  
  
}  