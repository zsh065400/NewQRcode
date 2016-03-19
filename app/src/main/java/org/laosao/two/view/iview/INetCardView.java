package org.laosao.two.view.iview;

import android.graphics.Bitmap;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface INetCardView extends ICardView {

	void showHeadImage(Bitmap bitmap);

	void showWaitDialog();

	void dismissWaitDialog();

	String[] getBean();
}
