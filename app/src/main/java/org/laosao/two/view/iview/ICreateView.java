package org.laosao.two.view.iview;

import android.graphics.Bitmap;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface ICreateView {

	void closeFam();

	void showWaitDialog();

	void dismissWaitDialog();

	void showBitmap(Bitmap bitmap);
}
