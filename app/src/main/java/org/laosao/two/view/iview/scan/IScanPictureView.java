package org.laosao.two.view.iview.scan;

import android.graphics.Bitmap;

import org.laosao.two.view.iview.ICreateView;

/**
 * @author 赵树豪
 * @version 1.0
 */
public interface IScanPictureView extends ICreateView {

	String getSendWord();

	void showSendWordDialog(String text);

	void setSendWord(String text);

	void showLargePicture(Bitmap bitmap);
}
