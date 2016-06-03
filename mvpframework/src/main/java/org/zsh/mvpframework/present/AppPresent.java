package org.zsh.mvpframework.present;

import android.content.Intent;

import org.zsh.mvpframework.view.IView;

/**
 * @author：Administrator
 * @version:1.0
 */
public abstract class AppPresent<T extends IView> implements IPresent {
	protected T mView;

	public AppPresent(T view) {
		this.mView = view;
	}

	@Override
	public void onCreate() {
		mView.initView();
		mView.setListener();
        mView.loadData();
	}

	@Override
	public void onDestory() {
		mView = null;
	}

	@Override
	public void onReseum() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onReStart() {

	}

	public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

}
