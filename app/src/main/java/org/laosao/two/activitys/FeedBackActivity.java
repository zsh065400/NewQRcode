package org.laosao.two.activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.FeedBackBmob;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class FeedBackActivity extends BaseActivity {
	private MaterialEditText etTheme;
	private MaterialEditText etContent;
	private MaterialEditText etContact;
	private ButtonFloat btnSendFb;
	private TextView tvShow;
	private TextView tvUpdate;

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
		tvShow = (TextView) findViewById(R.id.tvShow);
		tvUpdate = (TextView) findViewById(R.id.tvUpdate);
		btnSendFb.setOnClickListener(this);
		tvShow.setOnClickListener(this);
		tvUpdate.setOnClickListener(this);

		initViewData();
	}

	private void initViewData() {
		String version = null;
		try {
			PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			L.outputError("获取版本信息出错");
			e.printStackTrace();
			return;
		}
		tvUpdate.setText(getString(R.string.current_version) +
				version +
				getString(R.string.click_check_update));
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
				final MaterialDialog dialog = new MaterialDialog(this)
						.setTitle(getString(R.string.do_send))
						.setMessage(getString(R.string.please_wait)).
								setView(new ProgressBar(this));

				dialog.setCanceledOnTouchOutside(false);
				dialog.show();

				FeedBackBmob feedBackBmob = new FeedBackBmob(etTheme.getText().toString(),
						etContent.getText().toString(),
						etContact.getText().toString());
				BmobControl.insertObject(this, new BmobControl.BmobSaveCallback() {
					@Override
					public void onSuccess() {
						dialog.dismiss();
//						AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(FeedBackActivity.this);
//						builder.setTitle(R.string.fb_success);
//						builder.setMessage(R.string.fb_msg);
//						builder.setNegativeButton(getString(R.string.ok), null);
//						builder.create().show();
						final MaterialDialog dialog = new MaterialDialog(FeedBackActivity.this)
								.setTitle(getString(R.string.fb_success))
								.setMessage(getString(R.string.fb_msg))
								.setNegativeButton(getString(R.string.ok), null);
						dialog.show();

						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
					}

					@Override
					public void onFail(String error) {
						dialog.dismiss();
						T.showShortToast(FeedBackActivity.this, error);
					}
				}, feedBackBmob);
				break;
			case R.id.tvShow:
				MaterialDialog build = new MaterialDialog(this);
				build.setTitle(getString(R.string.title));
				build.setMessage(getString(R.string.click_hint));
				build.setPositiveButton(getString(R.string.see), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_VIEW);
								intent.addCategory(Intent.CATEGORY_DEFAULT);
								intent.setData(Uri.parse(getString(R.string.sina_url)));
								startActivity(intent);
							}
						}
				);
				build.setNegativeButton(getString(R.string.cancel), null);
				build.show();
				break;
			case R.id.tvUpdate:
				GeneralUtil.autoUpdate(Config.UPDATE_USER, FeedBackActivity.this);
				break;
		}

	}

}
