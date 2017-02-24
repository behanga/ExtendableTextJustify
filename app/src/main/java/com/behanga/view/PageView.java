package com.behanga.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.behanga.R;
import com.behanga.core.Config;
import com.behanga.core.Layout;
import com.behanga.element.Block;
import com.behanga.element.ImageBlock;
import com.behanga.element.Page;
import com.behanga.element.Paragraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class PageView extends View {
	private Layout mLayout;
	private boolean isMeasured;
	public Page mPage = null;
	private Paint mPaint;

	public PageView(Context context) {
		super(context);
		init();
	}

	public PageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init() {
		mLayout = new Layout();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(Config.getTextSize());
		mPaint.setColor(Config.getTextColor());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mLayout.setWidth(getMeasuredWidth());

		//For test
		String text1 = "We often speak to dogs and babies in a similar way" +
				" Scientists have decoded \"dog-directed speech\" for the first time, and they say puppies respond to it. " +
				"Puppies reacted positively and wanted to play when researchers played them a tape of phrases like, \"Who's a good boy?\"" +
				"\n" +
				"However, the international team of researchers found that adult dogs ignored this kind of speech.\n";
		String text2 = "When we talk to dogs, we often speak slowly in a high-pitched voice, similar to the way we talk to young babies. " +
				"The researchers think this way of talking may be our natural way of trying to interact with non-speaking listeners. " +
				"Prof Nicolas Mathevon of the University of Lyon/Saint-Etienne in France said pet-directed speech is similar to the way we talk to young infants, which is known to engage their attention and promote language learning.\n" +
				"\"We found that puppies are highly reactive to dog-directed speech, in the absence of any other cues, like visual cues,\" Prof Mathevon told BBC News.\n";
		String text3 = "\"Conversely we found that with adult dogs, they do not react differentially between dog-directed speech and normal speech.\""
				+ "The scientists recorded people saying the sentence: \"Hi! Hello cutie! Who's a good boy? Come here! Good boy! Yes! Come here sweetie pie! What a Good boy!\" as if they were speaking to a pet.\n" +
				"\n" +
				"This was played back through a loudspeaker to dogs of all ages and compared with normal speech.\n" +
				"\n" +
				"The researchers also found that human speakers use dog-directed speech with dogs of all ages even though it is only useful in puppies.\n" +
				"\n" +
				"\"Maybe this register of speech is used to engage interaction with a non-speaking [animal] rather than just a juvenile listener,\" said Prof Mathevon.\n" +
				"\n" +
				"Dogs have lived close to humans for thousands of years, which is reflected in mutual understanding and empathy.\n" +
				"\n" +
				"\"Dogs have been selected by humans for centuries to interact with us,\" he added. \"Maybe we have selected puppies that want to play or engage in interaction with us.\n" +
				"\n" +
				"\"And maybe older dogs do not react that way because they are just more choosy and they want only to react with a familiar person.\"\n";

		String text4 = "The experiment adds a new dimension to the idea that we talk differently to puppies because we are swayed by their cute ''baby-like'' appearance. " +
				"This theory - known as the baby schema - suggests we respond to the faces of baby animals in a similar way to those of human babies because we want to take care of them.\n" +
				"\n" +
				"\"One of the hypotheses was that we humans use this dog-directed speech because we are sensitive to the baby cues that come from the face of a small baby as we are sensitive to the faces of our babies,\" said Prof Mathevon.\n" +
				"\n" +
				"\"But actually our study demonstrates that we use pet-directed speech or infant-directed speech not only because of that but maybe we use this kind of speech pattern when we want to engage and interact with a non-speaking listener.\n" +
				"\n" +
				"\"Maybe this speaking strategy is used in any context when we feel that the listener may not fully master the language or has difficulty to understand us.\""
				+ "Dr David Reby, a psychologist at the University of Sussex, said the research could lead to better ways for humans to communicate with animals.\n" +
				"\n" +
				"\"There could be a practical use if we identify in the long term ways to speak to dogs that help and support their acquisition of new commands.\"\n" +
				"\n" +
				"The research is published in Proceedings of the Royal Society of London B- Biological Sciences.";
		if (!isMeasured) {
			mPage = new Page();
			List<Block> imageBlockList1 = new ArrayList<>();
			List<Block> imageBlockList2 = new ArrayList<>();

			ImageBlock imageBlock1 = new ImageBlock();
			ImageBlock imageBlock2 = new ImageBlock();
			ImageBlock imageBlock3 = new ImageBlock();

			Drawable drawable1 = getResources().getDrawable(R.drawable.img3);
			imageBlock1.width = drawable1.getIntrinsicWidth() / 4;
			imageBlock1.height = drawable1.getIntrinsicHeight() / 4;
			imageBlock1.setDrawable(drawable1);
			imageBlockList1.add(imageBlock1);

			Drawable drawable2 = getResources().getDrawable(R.drawable.img4);
			imageBlock2.width = drawable1.getIntrinsicWidth() / 4;
			imageBlock2.height = drawable1.getIntrinsicHeight() / 4;
			imageBlock2.left = 780;
			imageBlock2.top = 100;
			imageBlock2.setDrawable(drawable2);
			imageBlockList2.add(imageBlock2);

			Drawable drawable3 = getResources().getDrawable(R.drawable.img2);
			imageBlock3.left = 350;
			imageBlock3.top = 500;
			imageBlock3.width = drawable3.getIntrinsicWidth();
			imageBlock3.height = drawable3.getIntrinsicHeight();
			imageBlock3.setDrawable(drawable3);
			imageBlockList1.add(imageBlock3);


			long time = System.currentTimeMillis();
			Paragraph paragraph1 = mLayout.measure(text1, imageBlockList1);
			Paragraph paragraph2 = mLayout.measure(text2, imageBlockList2);
			Paragraph paragraph3 = mLayout.measure(text3);
			Paragraph paragraph4 = mLayout.measure(text4);
			paragraph1.setPageObserver(mPage);
			paragraph2.setPageObserver(mPage);
			paragraph3.setPageObserver(mPage);
			paragraph4.setPageObserver(mPage);

			mPage.paragraphs.add(paragraph1);
			mPage.paragraphs.add(paragraph2);
			mPage.paragraphs.add(paragraph3);
			mPage.paragraphs.add(paragraph4);

			mPage.setPageView(this);
			System.out.println("time:" + (System.currentTimeMillis() - time));
			isMeasured = true;
		}

		setMeasuredDimension(getMeasuredWidth(), mPage == null ? 0 : mPage.getHeight());

	}


	@Override
	protected void onDraw(Canvas canvas) {
		mPage.draw(canvas, mPaint, 0, 0, getMeasuredWidth(), mPage.getHeight());
	}
}
