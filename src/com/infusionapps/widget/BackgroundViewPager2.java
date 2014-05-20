package com.infusionapps.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class BackgroundViewPager2 extends ViewPager {

	private Drawable mBackgroundDrawable;

	public BackgroundViewPager2(Context context) {
		super(context);
	}

	public BackgroundViewPager2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TypedArray a = context.obtainStyledAttributes(attrs,
		// R.styleable.BackgroundViewPager);
		// mFactor = a.getInteger(R.styleable.BackgroundViewPager_factor, 3);

		// if (mFactor < 1) {
		// throw new IllegalArgumentException(
		// "Factor value can't be less than 1");
		// }

		// int bgRes = a.getResourceId(R.styleable.BackgroundViewPager_image,
		// -1);
		// if (bgRes > -1) {
		// mBackgroundDrawable = context.getResources().getDrawable(bgRes);
		// }
		//
		// a.recycle();
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		if (getAdapter() != null) {
			getAdapter().unregisterDataSetObserver(adapterObserver);
		}
		super.setAdapter(adapter);

		if (adapter != null) {
			adapter.registerDataSetObserver(adapterObserver);
		}
	}

	private DataSetObserver adapterObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
		}
	};

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		processDrawable();
	};

	float displacemtent = 0;

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);

		displacemtent = (position * getWidth()) + positionOffsetPixels;

	}

	// /////////////////////////

	public void setViewPagerBackground(int res) {
		Drawable drawable = getContext().getResources().getDrawable(res);
		setViewPagerBackground(drawable);
	}

	public void setViewPagerBackground(Bitmap bitmap) {
		Drawable drawable = null;
		if (bitmap != null) {
			drawable = new BitmapDrawable(getResources(), bitmap);
			return;
		}
		setViewPagerBackground(drawable);
	}

	public void setViewPagerBackground(Drawable drawable) {
		mBackgroundDrawable = drawable;
		processDrawable();
	}

	private void processDrawable() {
		if (mBackgroundDrawable == null) {
			return;
		}

		int vwidth = getWidth();
		int vheight = getHeight();
		int dwidth = mBackgroundDrawable.getIntrinsicWidth();
		int dheight = mBackgroundDrawable.getIntrinsicHeight();
		boolean shouldScaleHeight = true;

		if (!(mBackgroundDrawable instanceof BitmapDrawable)) {
			shouldScaleHeight = false;
			if (dwidth < 0 || dheight < 0) {
				dwidth = getWidth();
				dheight = getHeight();
			}
		}

		if (vwidth == 0 || vheight == 0) {
			return;
		}

		int pageCount = 1;
		if (getAdapter() != null) {
			pageCount = getAdapter().getCount();
		}

		float scale;
		float dx = 0, dy = 0;

		vwidth = vwidth * pageCount;

		if (dwidth * vheight > vwidth * dheight) {
			scale = (float) vheight / (float) dheight;
			dx = (vwidth - dwidth * scale) * 0.5f;
		} else {
			scale = (float) vwidth / (float) dwidth;
			dy = (vheight - dheight * scale) * 0.5f;
		}

		int scaledWidth;
		int scaledHeight;
		if (shouldScaleHeight) {
			dx += 0.5f;
			dy += 0.5f;
			scaledWidth = (int) (vwidth * scale);
			scaledHeight = (int) (vheight * scale);
		} else {
			dx = 0;
			dy = 0;
			scaledWidth = (int) (getRight() * scale);
			scaledHeight = getBottom();
		}

		dx -= (getCurrentItem() * getWidth());

		Rect viewBounds = new Rect((int) dx, (int) dy, scaledWidth,
				scaledHeight);

		mBackgroundDrawable.setBounds(viewBounds);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (mBackgroundDrawable != null) {
			mBackgroundDrawable.draw(canvas);
		}
		super.dispatchDraw(canvas);

	}
}
