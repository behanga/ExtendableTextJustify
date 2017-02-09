package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.behanga.core.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Paragraph extends Element {
	public List<Block> blocks = new ArrayList<>();
	public List<Line> lines = new ArrayList<>();
	private Rect mRect1 = new Rect();
	private Rect mRect2 = new Rect();
	private float mCurLeft; //加入line之后左边的位置
	private float mCurLineBottom;

	public Paragraph(float width) {
		this.width = width;
	}


	@Override
	public void notifyChange() {

	}

	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {
		if (blocks != null) {
			for (Block block : blocks) {
				block.draw(canvas, paint, left, top, right, bottom);
			}
		}

		if (lines != null) {
			float lastLineTop = -1;
			float lastLineHeight = -1;
			for (Line line : lines) {
				if (lastLineTop != -1 && lastLineTop == line.top) {
					top -= lastLineHeight;
				}
				line.draw(canvas, paint, left + line.left, top, right, bottom);

				top += line.height;
				lastLineTop = line.top;
				lastLineHeight = line.height;

			}
		}
	}

	public void addLine(Line line) {
		Line lastLine = (lines == null || lines.isEmpty()) ? null : lines.get(lines.size() - 1);

		if (mCurLeft >= width) {
			if (lastLine == null) {
				mCurLineBottom = 0;
			} else {
				mCurLineBottom = lastLine.top + lastLine.height;
			}
			mCurLeft = 0;
		} else {
			mCurLineBottom = lastLine == null ? 0 : lastLine.top;
		}
		line.top = mCurLineBottom;
		line.left = mCurLeft;
		line.width = width - mCurLeft;

		if (blocks != null) {
			calLineWidth(line, blocks);
			mCurLeft = Math.max(mCurLeft, line.left + line.width);
		} else {
			mCurLeft = width;
		}
		lines.add(line);
	}


	public int getHeight() {
		int blocksHeight = 0;
		int linesHeight = 0;

		if (blocks != null) {
			for (Block block : blocks) {
				blocksHeight = (int) Math.max(blocksHeight, block.top + block.height);
			}
		}

		if (lines != null) {
			float top = -100f;
			for (Line line : lines) {
				if (top < 0 || line.top != top) {
					linesHeight += line.height;
					top = line.top;
				}
			}
		}

		return Math.max(linesHeight, blocksHeight) + Config.getParagraphSpacing();
	}

	private boolean isRectOverlap(Rect r1, Rect r2) {
		int minX = Math.max(r1.left, r2.left);
		int minY = Math.max(r1.top, r2.top);
		int maxX = Math.min(r1.right, r2.right);
		int maxY = Math.min(r1.bottom, r2.bottom);

		return !(minX > maxX || minY > maxY);
	}

	/**
	 * calculate the line's width
	 *
	 * @param line
	 * @param blocks
	 */
	private void calLineWidth(Line line, List<Block> blocks) {
		for (Block block : blocks) {

			mRect1.set((int) block.left, (int) block.top, (int) (block.left + block.width), (int) (block.top + block.height));
			mRect2.set((int) line.left, (int) line.top, (int) (line.left + line.width), (int) (line.top + line.height));
			if (!isRectOverlap(mRect1, mRect2)) {
				continue;
			}
			if (line.left < block.left) {
				line.width = block.left - line.left;
				line.remainedWidth = line.width;
				mCurLeft = block.left + block.width;
			} else {
				line.left = Math.max(line.left, block.left + block.width);
				line.width = Math.max(0, width - (block.left + block.width));
				line.remainedWidth = line.width;
				mCurLeft = line.left + line.width;
			}
		}

	}
}
