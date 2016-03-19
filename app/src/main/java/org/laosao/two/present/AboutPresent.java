package org.laosao.two.present;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import org.laosao.two.R;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.AboutActivity;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class AboutPresent extends BasePresent<AboutActivity> {

	private final String mMsgTitle = "我发现了一个好玩又实用的应用：";
	private final String mMsgContent = "新二维码生成器，可以生成多种与众不同的二维码，等你下载来玩哦！" +
			"应用商店：http://app.mi.com/detail/64291?ref=search";

	public AboutPresent(Activity activity, AboutActivity view) {
		super(activity, view);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		loadData();
	}

	public void loadData() {
		String version = null;
		try {
			PackageInfo info = mActivity.getPackageManager().
					getPackageInfo(mActivity.getPackageName(), 0);
			version = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return;
		}
		mView.setVersion(version);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvInfo:
				mView.showDialog();
				break;

			case R.id.fabCheckUpdate:
				OtherUtils.autoUpdate(Config.UPDATE_USER, mActivity);
				break;

			case R.id.fabShare:
				OtherUtils.shareMsg(mActivity, "分享到",
						mMsgTitle,
						mMsgTitle + mMsgContent);
				break;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mView.closeFam();
	}
}
