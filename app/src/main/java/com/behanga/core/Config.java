package com.behanga.core;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/12/27.
 */

public class Config {

	private static final int DEFAULT_LINE_SPACING = 10;
	private static final int DEFAULT_WORD_SPACING = 15;
	private static final int DEFAULT_PARAGRAPH_SPACING = 55;
	private static final int DEFAULT_TEXT_SIZE = 55;
	private static final int DEFAULT_TEXT_COLOR = 0xff123456;

	@IntDef({STRATEGY_HYPHENATION_ENABLE, STRATEGY_HYPHENATION_DISABLE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Strategy {
	}

	public static final int STRATEGY_HYPHENATION_ENABLE = 1;
	public static final int STRATEGY_HYPHENATION_DISABLE = 2;

	private static int mStrategy = STRATEGY_HYPHENATION_ENABLE;
	private static int mLineSpacing = DEFAULT_LINE_SPACING;
	private static int mWordSpacing = DEFAULT_WORD_SPACING;
	private static int mParagraphSpacing = DEFAULT_PARAGRAPH_SPACING;
	private static int mTextSize = DEFAULT_TEXT_SIZE;
	private static int mTextColor = DEFAULT_TEXT_COLOR;

	public static void setStrategy(@Strategy int strategy) {
		mStrategy = strategy;
	}

	public static int getStrategy() {
		return mStrategy;
	}


	public static int getLineSpacing() {
		return mLineSpacing;
	}

	public static void setLineSpacing(int mLineSpacing) {
		Config.mLineSpacing = mLineSpacing;
	}

	public static int getWordSpacing() {
		return mWordSpacing;
	}

	public static void setWordSpacing(int mWordSpacing) {
		Config.mWordSpacing = mWordSpacing;
	}

	public static int getParagraphSpacing() {
		return mParagraphSpacing;
	}

	public static void setParagraphSpacing(int mParagraphSpacing) {
		Config.mParagraphSpacing = mParagraphSpacing;
	}

	public static int getTextSize() {
		return mTextSize;
	}

	public static void setTextSize(int mTextSize) {
		Config.mTextSize = mTextSize;
	}

	public static int getTextColor() {
		return mTextColor;
	}

	public static void setTextColor(int mTextColor) {
		Config.mTextColor = mTextColor;
	}


}
