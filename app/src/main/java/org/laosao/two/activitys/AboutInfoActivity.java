package org.laosao.two.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.gc.materialdesign.views.ButtonFloat;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.L;

public class AboutInfoActivity extends BaseActivity {


	private TextView mTvShow;
	private ButtonFloat mBtnShareApp;
	private TextView mTvUpdate;

	private final String mMsgTitle = "我发现了一个好玩又实用的应用：";
	private final String mMsgContent = "新二维码生成器，可以生成多种与众不同的二维码，等你下载来玩哦！" +
			"传送门：http://app.mi.com/detail/64291?ref=search";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_info);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initViews();
	}

	private void initViews() {
		mTvShow = (TextView) findViewById(R.id.tvShow);
		mBtnShareApp = (ButtonFloat) findViewById(R.id.btnShareApp);
		mTvUpdate = (TextView) findViewById(R.id.tvUpdate);

		mTvShow.setOnClickListener(this);
		mBtnShareApp.setOnClickListener(this);
		mTvUpdate.setOnClickListener(this);

		initViewData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvShow:
				AlertDialogWrapper.Builder build = new AlertDialogWrapper.Builder(this);
				build.setTitle(getString(R.string.title));
				build.setMessage(getString(R.string.click_hint));
				build.setPositiveButton(getString(R.string.see),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_VIEW);
								intent.addCategory(Intent.CATEGORY_DEFAULT);
								intent.setData(Uri.parse(getString(R.string.sina_url)));
								startActivity(intent);
							}
						});
				build.setNegativeButton(getString(R.string.cancel), null);
				build.show();
				break;
			case R.id.tvUpdate:
				GeneralUtil.autoUpdate(Config.UPDATE_USER, this);
				break;

			case R.id.btnShareApp:
				GeneralUtil.shareMsg(this, "分享：" + getString(R.string.app_name),
						mMsgTitle,
						mMsgTitle + mMsgContent,
						null);
				break;
		}
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
		mTvUpdate.setText(getString(R.string.current_version) +
				version +
				getString(R.string.click_check_update));
	}
}
