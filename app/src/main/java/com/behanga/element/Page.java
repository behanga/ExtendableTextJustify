package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Page {
	public List<Paragraph> paragraphs = new ArrayList<>();


	public void draw(Canvas canvas, Paint paint, int left, int top, int right, int bottom) {
		int paragraphTop = top;
		for (Paragraph paragraph : paragraphs) {
			paragraph.draw(canvas, paint, left, paragraphTop, right, bottom);
			paragraphTop += paragraph.getHeight();
		}
	}

	public int getHeight() {
		int height = 0;
		for (Paragraph paragraph : paragraphs) {
			height += paragraph.getHeight();
		}

		return height;
	}
}
