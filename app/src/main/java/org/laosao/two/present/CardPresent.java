package org.laosao.two.present;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.CardActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class CardPresent extends BasePresent<CardActivity> {
	public CardPresent(Activity activity, CardActivity view) {
		super(activity, view);
	}

	@Override
	public void onClick(View v) {
		String content = mView.getContent();
		if (TextUtils.isEmpty(content)) {
			mView.showToast("姓名不能为空", Toast.LENGTH_SHORT);
		} else {
			mView.create(content);
			mView.reset();
		}
	}
}
