package com.behanga.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.behanga.core.Config;

import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/29.
 */

public class Paragraph extends Element {
	public List<Block> blocks;
	public List<Line> lines;


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

}
