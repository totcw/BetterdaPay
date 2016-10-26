package com.betterda.betterdapay.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

	private int downX;
	private int downY;

	public HorizontalScrollViewPager(Context context) {
		super(context);
	}

	public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//getParent().requestDisallowInterceptTouchEvent(true);
				downX = (int) ev.getX();
				downY = (int) ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) ev.getX();
				int moveY = (int) ev.getY();

				int diffX = downX - moveX;
				int diffY = downY - moveY;

				if(Math.abs(diffX) > Math.abs(diffY)) { // 当前是横向滑动, 不能拦截

						getParent().requestDisallowInterceptTouchEvent(true);

				}
				break;
			default:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
