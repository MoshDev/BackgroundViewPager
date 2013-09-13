package com.infusionapps.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.infusionapps.backgroundviewpager.R;

public class BackgroundViewPager extends ViewPager {

	private float mBackgroundX = -1;
	private float fraction = 0.0f;
	private Rect mBackgroundOrginalRect;
	private RectF mBackgroundNewRect;
	private int mBitmapRes = -1;

	private int mFactor = 3;
	private Bitmap mBitmap;

	public BackgroundViewPager(Context context) {
		super(context);
	}

	public BackgroundViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.BackgroundViewPager);
		mFactor = a.getInteger(R.styleable.BackgroundViewPager_factor, 3);

		if (mFactor < 1) {
			throw new IllegalArgumentException(
					"Factor value can't be less than 1");
		}

		mBitmapRes = a.getResourceId(R.styleable.BackgroundViewPager_image, -1);

		a.recycle();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);

		float over = (position * getWidth()) + positionOffsetPixels;

		mBackgroundX = -over * fraction;

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		processBitmap();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		if (mBitmap != null) {

			int save = canvas.save();
			Rect rect = canvas.getClipBounds();
			canvas.translate(rect.left, rect.top);

			canvas.drawBitmap(mBitmap, mBackgroundOrginalRect, new RectF(
					mBackgroundX, 0, mBackgroundNewRect.right + mBackgroundX,
					mBackgroundNewRect.bottom), null);

			canvas.restoreToCount(save);

		}

		super.dispatchDraw(canvas);
	}

	private void processBitmap() {
		int w = getWidth();
		int h = getHeight();

		boolean isDrawable = false;

		if (mBitmapRes > -1) {

			Drawable drawable = getResources().getDrawable(mBitmapRes);

			if (drawable instanceof BitmapDrawable) {
				mBitmap = ((BitmapDrawable) drawable).getBitmap();
			} else {
				isDrawable = true;

				mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(mBitmap);

				drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());

				drawable.draw(canvas);

			}
		}

		if (mBitmap == null || w == 0 || h == 0) {
			return;
		}

		float count = 1;
		if (getAdapter() != null && getAdapter().getCount() > 0) {
			count = getAdapter().getCount();
		}

		int imgWidth = mBitmap.getWidth();
		int imgHeight = mBitmap.getHeight();

		float ratio = (float) imgWidth / (float) imgHeight;

		float width = w * (mFactor   );
		float height = (isDrawable ? imgHeight : width / ratio);

		mBackgroundOrginalRect = new Rect(0, 0, imgWidth, imgHeight);
		mBackgroundNewRect = new RectF(0, 0, width, height);

		Rect sizeRect = new Rect(0, 0, w * mFactor, h);

		fraction = ((float) sizeRect.width() / (float) ((float) w * (float) count));

	}

	public void setFlowImage(Bitmap bitmap) {
		mBitmap = bitmap;
		processBitmap();
	}

	public void setFlowImage(int resId) {
		mBitmap = BitmapFactory.decodeResource(getResources(), resId);
		processBitmap();
		invalidate();
		requestLayout();
	}

	public void setFlowFactor(int factor) {
		if (factor < 1) {
			throw new IllegalArgumentException(
					"Factor value can't be less than 1");
		}
		this.mFactor = factor;
		processBitmap();
		invalidate();
		requestLayout();
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		if (getAdapter() != null) {
			getAdapter().unregisterDataSetObserver(observer);
		}
		super.setAdapter(adapter);
		if (adapter == null) {
			processBitmap();
			invalidate();
			requestLayout();
			return;
		}
		adapter.registerDataSetObserver(observer);
	}

	private DataSetObserver observer = new DataSetObserver() {

		public void onChanged() {
			super.onChanged();
			processBitmap();
			invalidate();
			requestLayout();
		};

		public void onInvalidated() {
			super.onInvalidated();
			processBitmap();
			invalidate();
			requestLayout();
		};
	};
}
