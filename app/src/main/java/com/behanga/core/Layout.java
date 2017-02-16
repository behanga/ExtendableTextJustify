package com.behanga.core;


import android.graphics.Paint;

import com.behanga.element.Block;
import com.behanga.element.Line;
import com.behanga.element.Paragraph;
import com.behanga.element.Span;
import com.behanga.element.TextSpan;
import com.behanga.hyphenation.Hyphenator;

import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2016/12/30.
 */

public class Layout {
	private int mWidth;
	private Paint mPaint;
	private int mTextHeight;
	private Hyphenator mHyphenator;


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
	 * only text measure
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
		return measure(null, spans);
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


	/**
	 * text and img measure
	 *
	 * @param source
	 */
	private Paragraph measureEn(String source, List<Block> blocks) {
		int hyphenWidth = (int) mPaint.measureText("-");
		String[] words = source.split("\\s");
		Paragraph paragraph = new Paragraph(mWidth);
		if (blocks != null) {
			for (Block block : blocks) {
				block.setParagraphObserver(paragraph);
				paragraph.blocks.add(block);
			}
		}

		Line line = new Line(mWidth, mTextHeight);//第一行
		paragraph.addLine(line);

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			float wordWidth = mPaint.measureText(word);
			TextSpan span = createTextSpan(wordWidth, mTextHeight, word, TextSpan.TYPE_NONE_PART);
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

					line = new Line(mWidth, mTextHeight);
					paragraph.addLine(line);

					if (isAdded) {
						String tailwordPart = "";
						for (int j = pos; j < separatedWords.size(); j++) {
							tailwordPart += separatedWords.get(j);
						}
						if (!tailwordPart.isEmpty()) {
							float wordPartWidth = mPaint.measureText(tailwordPart);
							span = createTextSpan(wordPartWidth, mTextHeight, tailwordPart, TextSpan.TYPE_RIGHT_PART);

							while (!line.addSpan(span)) {
								line.height += Config.getLineSpacing();
								line = new Line(mWidth, mTextHeight);
								paragraph.addLine(line);
							}
						}
					} else {
						i--;
					}
				}
			}

		}
		line.justify();

		return paragraph;
	}

	private TextSpan createTextSpan(float width, float height, String text, int whichPart) {
		TextSpan span = new TextSpan();
		span.width = width;
		span.height = height;
		span.text = text;
		span.whichPart = whichPart;
		return span;
	}


}

