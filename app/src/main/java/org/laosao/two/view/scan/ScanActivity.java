package org.laosao.two.view.scan;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.zbar.lib.CaptureActivity;

import org.laosao.two.model.Config;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanActivity extends CaptureActivity implements SwipeBackActivityBase {

	private SwipeBackActivityHelper mHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
		mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	protected void decodeSuccess(String result) {
		boolean isPic = result.startsWith(Config.KEY_SCAN_PICTURE) &&
				result.endsWith("jpg") ||
				result.endsWith("bmp") ||
				result.endsWith("png") ||
				result.endsWith("gif") ||
				result.endsWith("jpeg");
		boolean isAudio = result.startsWith(Config.KEY_SCAN_PICTURE) &&
				result.endsWith("mp3") ||
				result.endsWith("wav") ||
				result.endsWith("wma") ||
				result.endsWith("ogg") ||
				result.endsWith("acc");
		Intent intent = null;
		Class c = null;
		if (result.startsWith(Config.KEY_SCAN_NET_CARD)) {
			result = result.substring(9, result.length());
			c = ScanNetCardActivity.class;
		} else if(result.startsWith(Config.KEY_SCAN_FILE) || result.startsWith("BEGIN:VCARD")){
			c = ScanTextActivity.class;
		}else if (isPic) {
			c = ScanPictureActivity.class;
		} else if (isAudio) {
			c = ScanAudioActivity.class;
		} else {
			c = ScanTextActivity.class;
		}
		intent = new Intent(this, c);
		intent.putExtra(Config.KEY_RESULT, result);
		startActivity(intent);
	}

	@Override
	protected void decodeFail() {
		// TODO: 2016/3/23 扫描失败后无法继续
		Toast.makeText(this, "扫描失败，请重试", Toast.LENGTH_SHORT).show();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		Utils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}

	@Override
	public void onBackPressed() {
		this.dispatchTouchEvent(
				MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(),
						MotionEvent.ACTION_DOWN, 0, 500, 0));
		this.dispatchTouchEvent(
				MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(),
						MotionEvent.ACTION_MOVE, 600, 500, 0));
		this.dispatchTouchEvent(
				MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(),
						MotionEvent.ACTION_UP, 600, 500, 0));
	}
}