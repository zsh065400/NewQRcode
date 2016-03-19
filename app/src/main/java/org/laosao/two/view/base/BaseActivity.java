package org.laosao.two.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.laosao.two.model.Config;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.CreateActivity;
import org.zsh.mvpframework.view.IView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public abstract class BaseActivity<T extends BasePresent> extends SwipeBackActivity
		implements View.OnClickListener, IView {

	protected T mPresent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//沉浸式状态栏下软键盘调试
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}

	@Override
	protected void onPause() {
		mPresent.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mPresent.onReseum();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mPresent.onDestory();
		try {
			finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		mPresent.onClick(v);
	}

	@Override
	public void create(String content) {
		Bundle data = new Bundle();
		data.putString(Config.KEY_CONTENT, content);
		toOtherActivity(CreateActivity.class, data);
	}

	@Override
	public void toOtherActivity(Class activity, Bundle bundle) {
		Intent intent = new Intent(this, activity);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void showToast(String msg, int time) {
		Toast.makeText(this, msg, time).show();
	}

	@Override
	public void showToast(int resId, int time) {
		Toast.makeText(this, resId, time).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mPresent.onActivityResult(requestCode, resultCode, data);
	}
}
