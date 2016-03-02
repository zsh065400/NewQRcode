package org.zsh.mvpframework.view;

/**
 * @author：Administrator
 * @version:1.0
 */
public interface IView {

	void showToast(String msg, int time);

	void showSimpleDialog(String title, String msg);

	void dismissDialog();

	void initView();

	void loadData();

	void setListener();

	void toOtherAty(Class atyClass);
}
