package com.uab.homescreenoverlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomImageView extends ImageView {

	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private GestureListener mGestureListener;
	private GestureDetector mGestureDetector;
	private Context context;

	public CustomImageView(Context context) {
		super(context);
		sharedConstructing(context);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructing(context);
	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		sharedConstructing(context);
	}

	private void sharedConstructing(Context context) {
		super.setClickable(true);

		this.context = context;
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		windowManager.addView(this, params);

		mGestureListener = new GestureListener();
		mGestureDetector = new GestureDetector(context, mGestureListener, null,
				true);

		setOnTouchListener(new OnTouchListener() {

			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					v.performClick();
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX
							+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
							+ (int) (event.getRawY() - initialTouchY);
					windowManager
							.updateViewLayout(CustomImageView.this, params);
					return true;
				}
				return false;
			}

		});
	}

	public void removeView() {
		windowManager.removeView(this);
	}

	public class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Toast.makeText(context, "Double Tapped", Toast.LENGTH_LONG).show();
			return true;
		}
	}
}
