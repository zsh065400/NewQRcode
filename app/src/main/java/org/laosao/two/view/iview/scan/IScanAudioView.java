package org.laosao.two.view.iview.scan;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface IScanAudioView {

	void changState(int state);

	void showWaitDialog();

	void dismissWaitDialog();

	void setAudioName(String s);
}
