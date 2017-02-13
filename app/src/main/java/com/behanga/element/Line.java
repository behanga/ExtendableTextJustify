package com.behanga.element;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.behanga.core.Config;
import com.behanga.core.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Line extends Element {
	public List<Span> spans = new ArrayList<>();

	private Paragraph mParagraph;
	private float remainedWidth;


	public Line(float width, float height) {
		this.width = width;
		this.height = height;
		this.remainedWidth = width;
	}


	@Override
	public void notifyChange(State state) {
		mParagraph.notifyChange(state);
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
		int wordSpacingWidth = Config.getWordSpacing();
		if (span.width <= remainedWidth) {  //正常能装入到line中
			if (index > 0 && index < spans.size()) {
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
