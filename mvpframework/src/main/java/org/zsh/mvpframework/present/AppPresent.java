package org.zsh.mvpframework.present;

import org.zsh.mvpframework.view.IView;

/**
 * @author：Administrator
 * @version:1.0
 */
public abstract class AppPresent<T extends IView> implements IPresent {
	private T mView;

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
		if (mView != null) {
			mView = null;
		}
	}

	@Override
	public void onReseum() {

	}
}
