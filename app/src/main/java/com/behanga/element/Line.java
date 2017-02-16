package com.behanga.element;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.behanga.core.Config;
import com.behanga.core.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Line extends Element {
	public List<Span> spans = new CopyOnWriteArrayList<>();
	private Rect mRect1 = new Rect();
	private Rect mRect2 = new Rect();
	private Paragraph mParagraph;
	private float mCurLeft; //加入line之后左边的位置
	private List<Fragment> mFragmentList = new ArrayList<>();
	private int mFragmentIndex = 0;


	public Line(float width, float height) {
		this.width = width;
		this.height = height;
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
		int spanCount = 0;
		for (int i = 0; i < mFragmentList.size(); i++) {
			Fragment fragment = mFragmentList.get(i);
			float sum = 0;
			for (int j = spanCount; j < spanCount + fragment.count; j++) {
				sum += spans.get(j).width;
			}
			float spacingWidth = (fragment.width - sum) / (fragment.count - 1);
			for (int index = spanCount + 1; index < spanCount + fragment.count; index++) {
				spans.get(index).left += (spacingWidth - Config.getWordSpacing()) * (index - spanCount);
			}
			spanCount += fragment.count;
		}
	}


	public boolean addSpan(Span span) {
		return addSpan(-1, span);
	}

	public boolean addSpan(int index, Span span) {
		int wordSpacingWidth = Config.getWordSpacing();
		for (; mFragmentIndex < mFragmentList.size(); mFragmentIndex++) {
			Fragment fragment = mFragmentList.get(mFragmentIndex);
			if (span.width <= fragment.remainedWidth) {  //正常能装入到line中
				if (index > 0 && index < spans.size()) {
					span.left = spans.get(index).left;
					spans.add(index, span);
					for (int i = index + 1; i < spans.size(); i++) {
						spans.get(index).left += span.width;
					}
				} else {
					span.left = fragment.left + fragment.width - fragment.remainedWidth;
					spans.add(span);
				}

				this.height = Math.max(span.height, this.height);
				span.setLineObserver(this);
				fragment.remainedWidth -= (span.width + wordSpacingWidth);
				fragment.count++;
				return true;
			}
		}
		return false;

	}

	public State onUpdate() {
//		remainedWidth = width;
		State state = new State();
//		int index = 0;
//		int wordSpacingWidth = Config.getWordSpacing();
//		for (int i = 0; i < spans.size(); i++) {
//			Span span = spans.get(i);
//			if (span.width <= remainedWidth) {
//				span.left = this.left + width - remainedWidth;
//				remainedWidth -= (span.width + wordSpacingWidth);
//			} else {
//				index = i;
//				state.changedList = spans.subList(index, spans.size());
//				spans.removeAll(state.changedList);
//				justify();
//				break;
//			}
//		}

		return state;
	}


	/**
	 * calculate the line's width
	 *
	 * @param blocks
	 */
	public void calWidth(List<Block> blocks) {
		mCurLeft = this.left;
		while (mCurLeft < width) {
			Fragment fragment = new Fragment(mCurLeft, width - mCurLeft);
			mFragmentList.add(fragment);
			for (Block block : blocks) {
				mRect1.set((int) block.left, (int) block.top, (int) (block.left + block.width), (int) (block.top + block.height));
				mRect2.set((int) fragment.left, (int) this.top, (int) (fragment.left + fragment.width), (int) (this.top + this.height));
				if (!isRectOverlap(mRect1, mRect2)) {
					continue;
				}
				if (fragment.left < block.left) {
					fragment.width = block.left - fragment.left;
					fragment.remainedWidth = fragment.width;
					mCurLeft = block.left + block.width;
				} else {
					fragment.left = Math.max(fragment.left, block.left + block.width);
					fragment.width = Math.max(0, width - (block.left + block.width));
					fragment.remainedWidth = fragment.width;
					mCurLeft = fragment.left + fragment.width;
				}
			}
			mCurLeft = Math.max(mCurLeft, fragment.left + fragment.width);
		}

	}

	private boolean isRectOverlap(Rect r1, Rect r2) {
		int minX = Math.max(r1.left, r2.left);
		int minY = Math.max(r1.top, r2.top);
		int maxX = Math.min(r1.right, r2.right);
		int maxY = Math.min(r1.bottom, r2.bottom);

		return !(minX > maxX || minY > maxY);
	}


	private class Fragment {
		float left;
		float width;
		int count;
		float remainedWidth;

		public Fragment(float left, float width) {
			this.left = left;
			this.width = width;
			remainedWidth = width;
		}
	}


}
