package org.laosao.two.present;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.EmailActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class EmailPresent extends BasePresent<EmailActivity> {

	public EmailPresent(Activity activity, EmailActivity view) {
		super(activity, view);
	}

	@Override
	public void onClick(View v) {
		String content = mView.getContent();
		if (TextUtils.isEmpty(content)) {
			mView.showToast("邮件不完整", Toast.LENGTH_SHORT);
			return;
		}
		mView.create(content);
		mView.reset();
	}
}
