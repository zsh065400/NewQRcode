package org.laosao.two.view.iview;

/**
 * @author 赵树豪
 * @version 1.0
 */

import org.zsh.mvpframework.view.IView;

import java.io.File;

public interface IAudioView extends IView {

	void pickAudioForResult();

	void startRecord();

	void showUploadDialog();

	void showSaveDialog(File file);

	void setFileName(String fileName);

	void setFileName(int resId);

	void dissmissDialog();

}
