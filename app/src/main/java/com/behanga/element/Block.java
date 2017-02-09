package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Block extends Element {


	private Paragraph mParagraph;


	public void setParagraphObserver(Paragraph paragraph) {
		this.mParagraph = paragraph;
	}

	@Override
	public void notifyChange() {
		if (mParagraph != null) {
			mParagraph.notifyChange();
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {

	}
}
