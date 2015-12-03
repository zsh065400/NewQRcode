package org.laosao.two.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.FeedBackBmob;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.T;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class FeedBackActivity extends BaseActivity {
	private MaterialEditText etTheme;
	private MaterialEditText etContent;
	private MaterialEditText etContact;
	private ButtonFloat btnSendFb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
	}

	private void initView() {
		etTheme = (MaterialEditText) findViewById(R.id.etTheme);
		etContent = (MaterialEditText) findViewById(R.id.etContent);
		etContact = (MaterialEditText) findViewById(R.id.etContact);
		btnSendFb = (ButtonFloat) findViewById(R.id.btnSendFb);
		btnSendFb.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSendFb:
				String theme = etTheme.getText().toString();
				String content = etContent.getText().toString();
				String contact = etContact.getText().toString();
				if (TextUtils.isEmpty(theme) || TextUtils.isEmpty(content) || TextUtils.isEmpty(contact)) {
					T.showLongToast(this, getString(R.string.please_write_done));
					return;
				}
				final MaterialDialog dialog = new MaterialDialog.Builder(this)
						.title(getString(R.string.do_send))
						.content(getString(R.string.please_wait))
						.progress(true, 0)
						.show();
				dialog.setCanceledOnTouchOutside(false);

				FeedBackBmob feedBackBmob = new FeedBackBmob(etTheme.getText().toString(),
						etContent.getText().toString(),
						etContact.getText().toString());
				BmobControl.insertObject(this, new BmobControl.BmobSaveCallback() {
					@Override
					public void onSuccess() {
						dialog.dismiss();
						AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(FeedBackActivity.this);
						builder.setTitle(R.string.fb_success);
						builder.setMessage(R.string.fb_msg);
						builder.setNegativeButton(getString(R.string.ok), null);
						builder.create().show();
					}

					@Override
					public void onFail(String error) {
						dialog.dismiss();
						T.showShortToast(FeedBackActivity.this, error);
					}
				}, feedBackBmob);
				break;


		}

	}

}
