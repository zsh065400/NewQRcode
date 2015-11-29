package org.laosao.two.view;

/*
 * Copyright (C) 2013 Muthuramakrishnan <siriscac@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import org.laosao.two.R;

@SuppressLint("ClickableViewAccessibility")
public class RippleView extends Button {

	private float mDownX;
	private float mDownY;
	private float mAlphaFactor;
	private float mDensity;
	private float mMaxRadius;
	private int mDuration;
	private int mRadius = 15;

	private int mRippleColor;
	private boolean mIsAnimating = false;
	private boolean mHover = true;

	private RadialGradient mRadialGradient;
	private Paint mPaint;
	private ObjectAnimator mRadiusAnimator;

	private int dp(int dp) {
		return (int) (dp * mDensity + 0.5f);
	}

	public RippleView(Context context) {
		this(context, null);
	}

	public RippleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RippleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RippleView);
		mRippleColor = a.getColor(R.styleable.RippleView_rippleViewColor,
				Color.BLUE);
		mAlphaFactor = a.getFloat(R.styleable.RippleView_alphaFactor,
				1);
		mHover = a.getBoolean(R.styleable.RippleView_hover, false);
		mDuration = a.getInt(R.styleable.RippleView_duration, 400);
		init();
		a.recycle();
	}

	public void init() {
		mDensity = getContext().getResources().getDisplayMetrics().density;

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAlpha(100);
//		setRippleColor(mRippleColor);
//		setAlphaFactor(mAlphaFactor);
//		setHover(mHover);
//		setDuration(mDuration);
		Log.d("the color is  -------->", mRippleColor + "");
		Log.d("the alpha is  -------->", mAlphaFactor + "");
		Log.d("the hover is  -------->", mHover + "");
		Log.d("the duation is-------->", mDuration + "");
	}

	public void setRippleColor(int rippleColor) {
		mRippleColor = rippleColor;
	}

	public void setAlphaFactor(float alphaFactor) {
		mAlphaFactor = alphaFactor;
	}

	public void setHover(boolean enabled) {
		mHover = enabled;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mMaxRadius = (float) Math.sqrt(w * w + h * h);
	}

	private boolean mAnimationIsCancel;
	private Rect mRect;


	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		Log.d("TouchEvent", String.valueOf(event.getActionMasked()));
		Log.d("mIsAnimating", String.valueOf(mIsAnimating));
		Log.d("mAnimationIsCancel", String.valueOf(mAnimationIsCancel));
		boolean superResult = super.onTouchEvent(event);
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN
				&& this.isEnabled() && mHover) {
			mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
			mAnimationIsCancel = false;
			mDownX = event.getX();
			mDownY = event.getY();

			mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", 0, dp(mRadius))
					.setDuration(mDuration);
			mRadiusAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			mRadiusAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animator) {
					mIsAnimating = true;
				}

				@Override
				public void onAnimationEnd(Animator animator) {
					setRadius(0);
					ViewHelper.setAlpha(RippleView.this, 1);
					mIsAnimating = false;
				}

				@Override
				public void onAnimationCancel(Animator animator) {

				}

				@Override
				public void onAnimationRepeat(Animator animator) {

				}
			});
			mRadiusAnimator.start();
			if (!superResult) {
				return true;
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_MOVE
				&& this.isEnabled() && mHover) {
			mDownX = event.getX();
			mDownY = event.getY();

			// Cancel the ripple animation when moved outside
			if (mAnimationIsCancel = !mRect.contains(
					getLeft() + (int) event.getX(),
					getTop() + (int) event.getY())) {
				setRadius(0);
			} else {
				setRadius(dp(mRadius));
			}
			if (!superResult) {
				return true;
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_UP
				&& !mAnimationIsCancel && this.isEnabled()) {
			mDownX = event.getX();
			mDownY = event.getY();

			final float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY
					* mDownY);
			float targetRadius = Math.max(tempRadius, mMaxRadius);

			if (mIsAnimating) {
				mRadiusAnimator.cancel();
			}
			mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", dp(mRadius),
					targetRadius);
			mRadiusAnimator.setDuration(mDuration);
			mRadiusAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			mRadiusAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animator) {
					mIsAnimating = true;
				}

				@Override
				public void onAnimationEnd(Animator animator) {
					setRadius(0);
					ViewHelper.setAlpha(RippleView.this, 1);
					mIsAnimating = false;
				}

				@Override
				public void onAnimationCancel(Animator animator) {

				}

				@Override
				public void onAnimationRepeat(Animator animator) {

				}
			});
			mRadiusAnimator.start();
			if (!superResult) {
				return true;
			}
		}
		return superResult;
	}

	public int adjustAlpha(int color, float factor) {
		int alpha = Math.round(Color.alpha(color) * factor);
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		return Color.argb(alpha, red, green, blue);
	}

	public void setRadius(final int radius) {
		mRadius = radius;
		if (mRadius > 0) {
			mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius,
					adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
					Shader.TileMode.MIRROR);
			mPaint.setShader(mRadialGradient);
		}
		invalidate();
	}

	private Path mPath = new Path();

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode()) {
			return;
		}

		canvas.save(Canvas.CLIP_SAVE_FLAG);

		mPath.reset();
		mPath.addCircle(mDownX, mDownY, mRadius, Path.Direction.CW);

		canvas.clipPath(mPath);
		canvas.restore();

		canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
	}

}
