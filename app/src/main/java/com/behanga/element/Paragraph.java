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

public class Paragraph extends Element {
	public List<Block> blocks = new ArrayList<>();
	public List<Line> lines = new ArrayList<>();
	private Page mPage;

	public Paragraph(float width) {
		this.width = width;
	}


	@Override
	public void notifyChange(State state) {
		if (state.beginer instanceof Block) {
			handlerBlockChange();
		} else if (state.beginer instanceof Line) {
			handlerLineChange((Line) state.beginer);
		}

		mPage.notifyChange();
	}

	public void setPageObserver(Page page) {
		this.mPage = page;
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
		line.setParagraphObserver(this);
		lines.add(line);
		if (lastLine == null) {
			line.top = 0;
		} else {
			line.top = lastLine.top + lastLine.height;
		}

		if (blocks != null) {
			line.calWidth(blocks);
		}
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


	private void handlerBlockChange() {

		for (Line line : lines) {
//			calLineWidth(line, blocks);
			line.onUpdate();
		}
	}


	private void handlerLineChange(Line beginer) {
		for (Line line : lines) {
			if (line.equals(beginer)) {

			}
		}
	}

}
