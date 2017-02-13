package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.behanga.core.State;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Block extends Element {


	private Paragraph mParagraph;


	public void setParagraphObserver(Paragraph paragraph) {
		this.mParagraph = paragraph;
	}

	@Override
	public void notifyChange(State state) {
		if (mParagraph != null) {
			mParagraph.notifyChange(state);
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {

	}
}
