package org.laosao.two.present;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.CustomActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class CustomPresent extends BasePresent<CustomActivity> {

	public CustomPresent(Activity activity, CustomActivity view) {
		super(activity, view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		String content = mView.getContent();
		if (TextUtils.isEmpty(content)) {
			mView.showToast(R.string.please_write_done, Toast.LENGTH_SHORT);
			return;
		}
		mView.setText(Config.EMPTY_STR);
		mView.create(content);
	}
}
