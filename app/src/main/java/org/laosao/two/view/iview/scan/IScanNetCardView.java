package org.laosao.two.view.iview.scan;

import android.view.View;

import org.laosao.two.bean.PersonIdCard;
import org.zsh.mvpframework.view.IView;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface IScanNetCardView extends IView {

	void setContent(PersonIdCard p);

	void showWaitDialog();

	void dismissWaitDialog();

	View getView();

	void showDialog(String msg);

	String getIntroduce();

}
