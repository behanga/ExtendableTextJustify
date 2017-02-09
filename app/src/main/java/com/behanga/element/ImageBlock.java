package com.behanga.element;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by lichen8974@gmail.com on 2017/1/16.
 */

public class ImageBlock extends Block {

	private Drawable mDrawable;
	private Bitmap mBitmap;
	private String mImageUrl;

	public ImageBlock() {
		left = 0;
		top = 0;
	}

	public void setDrawable(Drawable drawable) {
		if (drawable != null) {
			mDrawable = drawable;
		} else {
			mDrawable = null;
		}
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public void setImageUrl(String url) {
		mImageUrl = url;
	}


	@Override
	public void draw(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {

		if (mDrawable != null) {
			int drawableLeft = (int) (left + this.left);
			int drawableTop = (int) (top + this.top);
			int drawableRight = (int) (drawableLeft + width);
			int drawableBottom = (int) (drawableTop + height);

			mDrawable.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
			mDrawable.draw(canvas);
		} else if (mBitmap != null) {

		}

	}
}
