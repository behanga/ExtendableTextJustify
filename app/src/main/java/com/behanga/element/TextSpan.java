package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class TextSpan extends Span {
	public static final int TYPE_LEFT_PART = 1;
	public static final int TYPE_RIGHT_PART = 2;
	public static final int TYPE_NONE_PART = 0;

	public String text;
	public int whichPart;

	public TextSpan() {
		isDivisibility = true;
	}

	@Override
	public void notifyChange() {

	}

	@Override
	public Span createSpan() {
		return this;
	}

	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {

		String finalText = whichPart == TYPE_LEFT_PART ? text + "-" : text;
		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
		canvas.drawText(finalText, left, top + height - fontMetrics.descent, paint);
	}

}
