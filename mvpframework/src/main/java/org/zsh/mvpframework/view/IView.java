package org.zsh.mvpframework.view;

import android.os.Bundle;

/**
 * @author：Administrator
 * @version:1.0
 */
public interface IView {

	void showToast(String msg, int time);

	void showToast(int resId, int time);

//	void showSimpleDialog(String title, String msg);

//	void dismissDialog();

	void initView();

//	void loadData();

	void setListener();

	void toOtherActivity(Class activity, Bundle bundle);

	void create(String content);

	String getContent();

	void hideInput();
}
