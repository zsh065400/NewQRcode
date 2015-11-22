package org.laosao.two.activitys.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.laosao.two.activitys.CreateActivity;
import org.laosao.two.Config;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class BaseActivity extends Activity implements GestureDetector.
		                                                      OnGestureListener, View.OnClickListener {

	private GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		detector = new GestureDetector(this, this);
//		沉浸式状态栏下软键盘调试
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
				                             WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}


	/**
	 * 将触屏事件交给手势
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	/**
	 * 预先处理手势操作
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		detector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (e2.getX() - e1.getX() > 265) {
			this.finish();
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	@Override
	public void onClick(View v) {

	}

	protected void startCreateActivity(String content){
		Intent i = new Intent(this, CreateActivity.class);
		i.putExtra(Config.KEY_CONTENT, content);
		startActivity(i);
	}

	@Override
	protected void onDestroy() {
		try {
			finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		super.onDestroy();
	}
}
