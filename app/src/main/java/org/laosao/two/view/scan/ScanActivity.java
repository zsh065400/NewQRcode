package org.laosao.two.view.scan;

import android.os.Bundle;
import android.view.View;

import com.zbar.lib.CaptureActivity;

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


	}

	@Override
	protected void decodeFail() {

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
}