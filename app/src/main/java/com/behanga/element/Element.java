package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public abstract class Element {
	public float left = 0;
	public float top = 0;
	public float width = 0;
	public float height = 0;

	public abstract void notifyChange();


	public abstract void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom);
}
