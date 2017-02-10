package com.behanga.element;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.behanga.core.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Line extends Element {
	private Paragraph mParagraph;

	public List<Span> spans = new ArrayList<>();
	public float remainedWidth;


	public Line(float width, float height) {
		this.width = width;
		this.height = height;
		this.remainedWidth = width;
	}


	@Override
	public void notifyChange() {

	}

	public void setParagraphObserver(Paragraph paragraph) {
		this.mParagraph = paragraph;
	}

	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {
		for (Span span : spans) {
			span.draw(canvas, paint, span.left, top, right, bottom);
		}
	}

	public void justify() {
		if (this.spans.size() == 1) {
			return;
		}
		float sum = 0;
		for (int i = 0; i < spans.size(); i++) {
			sum += spans.get(i).width;
		}

		float spacingWidth = (width - sum) / (spans.size() - 1);
		for (int i = 1; i < spans.size(); i++) {
			spans.get(i).left += (spacingWidth - Config.getWordSpacing()) * i;
		}
	}


	public boolean addSpan(Span span) {
		return addSpan(-1, span);
	}

	public boolean addSpan(int index, Span span) {
		if (index > spans.size()) {
			return false;
		}

		int wordSpacingWidth = Config.getWordSpacing();
		if (span.width <= remainedWidth) {  //正常能装入到line中
			if (index < spans.size() || index > 0) {
				span.left = spans.get(index).left;
				spans.add(index, span);
				for (int i = index + 1; i < spans.size(); i++) {
					spans.get(index).left += span.width;
				}
			} else {
				span.left = this.left + width - remainedWidth;
				spans.add(span);
			}

			this.height = Math.max(span.height, this.height);
			span.setLineObserver(this);
			remainedWidth -= (span.width + wordSpacingWidth);
			return true;
		} else {
			return false;
		}

	}


}
