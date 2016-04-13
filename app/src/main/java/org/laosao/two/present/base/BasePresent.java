package org.laosao.two.present.base;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import org.zsh.mvpframework.present.AppPresent;
import org.zsh.mvpframework.view.IView;
import org.zsh.permission.Permission;
import org.zsh.permission.callback.IHandleCallback;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class BasePresent<T extends IView> extends AppPresent<T>
		implements IHandleCallback {
	public Activity mActivity;
	public IHandleCallback mCallback;

	public BasePresent(Activity activity, T view) {
		super(view);
		this.mActivity = activity;
		this.mCallback = this;
		onCreate();
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestory() {
		super.onDestory();
		mActivity = null;
		mCallback = null;
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

	public void requestPermission(String... permission) {
		Permission.getInstance().request(mCallback, mActivity,
				permission);
	}

	public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
		Permission.getInstance().onRequestPermissionsResult(permissions, grantResults);
	}

	public void onClick(View v) {

	}

	@Override
	public void granted(String[] permission) {
	}

	@Override
	public void denied(String[] permission) {
	}
}
