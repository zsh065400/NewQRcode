package org.laosao.two.present.base;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import org.zsh.mvpframework.present.AppPresent;
import org.zsh.mvpframework.view.IView;
import org.zsh.permission.callback.IHandleCallback;
import org.zsh.permission.handle.Request;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class BasePresent<T extends IView> extends AppPresent<T> implements IHandleCallback {
	protected Activity mActivity;

	public BasePresent(Activity activity, T view) {
		super(view);
		this.mActivity = activity;
		onCreate();
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestory() {
		super.onDestory();
		if (mActivity != null)
			mActivity = null;
	}

	@Override
	public void onReseum() {
		super.onReseum();
	}


	@Override
	public void onPause() {
		super.onPause();
		mView.hideInput();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	public void requestPermission(String[] permissions) {
		Request.getInstance(mActivity).execute(this, permissions);
	}

	/*处理权限*/
	@Override
	public void granted(String[] permission) {

	}

	@Override
	public void denied(String[] permission) {

	}

	public void onClick(View v) {

	}
}
