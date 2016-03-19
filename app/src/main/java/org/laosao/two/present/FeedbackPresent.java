package org.laosao.two.present;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.FeedBackBmob;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.FeedBackActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.dialog.MaterialDialog;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class FeedbackPresent extends BasePresent<FeedBackActivity> {
	public FeedbackPresent(Activity activity, FeedBackActivity view) {
		super(activity, view);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fabSend:
				String content = mView.getContent();
				if (TextUtils.isEmpty(content)) {
					mView.showToast(R.string.please_write_done, Toast.LENGTH_SHORT);
					return;
				}
				String[] split = content.split(":");
				String contact = split[2];
				if (!TextUtils.isEmpty(contact)) {
					Pattern pattern = Pattern.compile("[a-zA-z0-9._]{3,}@(\\w)+.[a-z]");
					Matcher matcher = pattern.matcher(contact);
					if (!matcher.matches()) {
						mView.showToast("邮箱格式有误，请重新输入", Toast.LENGTH_SHORT);
						return;
					}
				}
				final MaterialDialog dialog = OtherUtils.showWaitDialog(mActivity);
				FeedBackBmob feedBackBmob = new FeedBackBmob(split[0],
						split[1],
						split[2]);
				BmobControl.insertObject(mActivity, new BmobControl.BmobSaveCallback() {
					@Override
					public void onSuccess() {
						dialog.dismiss();
						mView.showDialog();
					}

					@Override
					public void onFail(String error) {
						dialog.dismiss();
						mView.showToast(error, Toast.LENGTH_SHORT);
					}
				}, feedBackBmob);
				break;
		}
	}
}
