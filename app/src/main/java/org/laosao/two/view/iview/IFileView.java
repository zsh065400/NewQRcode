package org.laosao.two.view.iview;

import org.zsh.mvpframework.view.IView;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface IFileView extends IView {

	void showUploadDialog();

	void setFileName(String fileName);

	void dissmissDialog();

	void getFile();
}
