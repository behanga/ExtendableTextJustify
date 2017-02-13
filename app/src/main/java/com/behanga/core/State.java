package com.behanga.core;

import android.support.annotation.IntDef;

import com.behanga.element.Element;
import com.behanga.element.Span;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichen8974@gmail.com on 2017/2/14.
 */

public class State {
	public static final int NO_CHANGE = 0;
	public static final int INCREASE = 1;
	public static final int DECREASE = 2;

	@IntDef({INCREASE, DECREASE, NO_CHANGE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface StateChange {
	}

	public
	@StateChange
	int widthChangeType;
	public
	@StateChange
	int heightChangeType;
	public List<Span> changedList = new ArrayList<>();
	public Element beginer;

}
