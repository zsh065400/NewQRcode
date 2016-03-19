package org.laosao.two.view.iview.scan;

import org.zsh.mvpframework.view.IView;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface IScanTextView extends IView {

	void setScanResult(String text);

	void changeFabState();

}
