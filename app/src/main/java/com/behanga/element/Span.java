package com.behanga.element;


import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public abstract class Span extends Element {
	private Line mLine;
	public boolean isDivisibility = false;

	public abstract Span createSpan();


	public void setLineObserver(Line line) {
		this.mLine = line;
	}


	public abstract void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom);

	public void notifyChanged() {
		if (mLine != null) {
			mLine.notifyChange();
		}
	}


}
