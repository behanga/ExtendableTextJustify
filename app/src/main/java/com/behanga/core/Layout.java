package com.behanga.core;


import android.graphics.Paint;
import android.graphics.Rect;

import com.behanga.element.Block;
import com.behanga.element.Line;
import com.behanga.element.Paragraph;
import com.behanga.element.Span;
import com.behanga.element.TextSpan;
import com.behanga.hyphenation.Hyphenator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/30.
 */

public class Layout {
	private int mWidth;
	private String mSource;
	private Paint mPaint;
	private int mTextHeight;
	private Hyphenator mHyphenator;
	private float mCurLeft; //加入line之后左边的位置
	private Rect mRect1 = new Rect();
	private Rect mRect2 = new Rect();


	public Layout() {
		mPaint = new Paint();
		mPaint.setTextSize(Config.getTextSize());


		Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
		mTextHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
		mHyphenator = Hyphenator.getInstance(Hyphenator.HyphenPattern.EN_US);
	}


	public void setWidth(int width) {
		mWidth = width;
	}

	/**
	 * 文字和图片测量
	 * <p>
	 * 每个block的位置只相对于它所在的paragraph的位置,就像View在ViewGroup的位置一样
	 *
	 * @param source
	 */
	public Paragraph measure(String source, List<Block> blocks) {

		return measureEn(source, blocks);
	}

	/**
	 * 纯文字测量
	 *
	 * @param source
	 */
	public Paragraph measure(String source) {
		return measureEn(source, null);
	}


	/**
	 * Span测量,包括可扩展的span
	 *
	 * @param spans
	 */
	public Paragraph measure(List<Span> spans) {


		return null;
	}


	/**
	 * 处理段落中包含图文混排的情况
	 *
	 * @param blocks
	 * @param spans
	 * @return
	 */
	public Paragraph measure(List<Block> blocks, List<Span> spans) {

		return null;
	}


	private Paragraph measureEn(String source, List<Block> blocks) {
		int hyphenWidth = (int) mPaint.measureText("-");
		float curLineBottom = 0;
		List<Line> lines = new ArrayList<>();
		String[] words = source.split("\\s");
		Line line = null;

		for (int i = 0; i < words.length; i++) {
			if (line == null) {
				line = new Line(0, 0, mWidth, mTextHeight);
				lines.add(line);
				if (blocks != null) {
					calLineWidth(line, blocks);//第一行
					mCurLeft = line.left + line.width;
				} else {
					mCurLeft = mWidth;
				}
			}

			String word = words[i];
			float wordWidth = mPaint.measureText(word);
			TextSpan span = createSpan(wordWidth, mTextHeight, word, TextSpan.TYPE_NONE_PART);
			if (!line.addSpan(span)) {
				//断字处理
				if (Config.getStrategy() == Config.STRATEGY_HYPHENATION_ENABLE) {
					List<String> separatedWords = mHyphenator.hyphenate(word);
					boolean isAdded = false;
					int pos;
					for (pos = separatedWords.size() - 1; pos >= 0; pos--) {
						String tailWordPart = separatedWords.get(pos);
						span.width = wordWidth + hyphenWidth - mPaint.measureText(tailWordPart);
						if (pos != 0 && line.addSpan(span)) {
							String headWordPart = "";
							for (int index = 0; index < pos; index++) {
								headWordPart += separatedWords.get(index);
							}
							span.text = headWordPart;
							span.whichPart = TextSpan.TYPE_LEFT_PART;
							isAdded = true;
							break;
						}
					}
					line.height += Config.getLineSpacing();
					line.justify();

					if (mCurLeft >= mWidth) {
						curLineBottom = line.top + line.height;
						mCurLeft = 0;
					} else {
						curLineBottom = line.top;
					}

					line = new Line(curLineBottom, mCurLeft, mWidth - mCurLeft, mTextHeight);
					lines.add(line);
					if (blocks != null) {
						calLineWidth(line, blocks);
						mCurLeft = Math.max(mCurLeft, line.left + line.width);
					} else {
						mCurLeft = mWidth;
					}

					if (isAdded) {
						String tailwordPart = "";
						for (int j = pos; j < separatedWords.size(); j++) {
							tailwordPart += separatedWords.get(j);
						}
						if (!tailwordPart.isEmpty()) {
							float wordPartWidth = mPaint.measureText(tailwordPart);
							span = createSpan(wordPartWidth, mTextHeight, tailwordPart, TextSpan.TYPE_RIGHT_PART);

							while (!line.addSpan(span)) {
								line.height += Config.getLineSpacing();
								if (mCurLeft >= mWidth) {
									curLineBottom = line.top + line.height;
									mCurLeft = 0;
								} else {
									curLineBottom = line.top;
								}

								line = new Line(curLineBottom, mCurLeft, mWidth - mCurLeft, mTextHeight);
								lines.add(line);
								if (blocks != null) {
									calLineWidth(line, blocks);
									mCurLeft = Math.max(mCurLeft, line.left + line.width);
								} else {
									mCurLeft = mWidth;
								}
							}
						}
					} else {
						i--;
					}
				}
			}

		}


		Paragraph paragraph = new Paragraph();
		paragraph.lines = lines;
		paragraph.blocks = blocks;
		return paragraph;
	}

	private TextSpan createSpan(float width, float height, String text, int whichPart) {
		TextSpan span = new TextSpan();
		span.width = width;
		span.height = height;
		span.text = text;
		span.whichPart = whichPart;

		return span;
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
				line.width = Math.max(0, mWidth - (block.left + block.width));
				line.remainedWidth = line.width;
				mCurLeft = line.left + line.width;
			}
		}
	}


}
