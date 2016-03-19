package org.laosao.two.present;

import android.app.Activity;
import android.view.View;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.AboutActivity;
import org.laosao.two.view.AudioActivity;
import org.laosao.two.view.CardActivity;
import org.laosao.two.view.CustomActivity;
import org.laosao.two.view.EmailActivity;
import org.laosao.two.view.FeedBackActivity;
import org.laosao.two.view.MainActivity;
import org.laosao.two.view.NetCardActivity;
import org.laosao.two.view.PictureActivity;
import org.laosao.two.view.SmsActivity;
import org.laosao.two.view.TextActivity;
import org.laosao.two.view.UrlActivity;
import org.laosao.two.view.WifiActivity;
import org.laosao.two.view.scan.ScanActivity;

import cn.bmob.v3.Bmob;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class MainPresent extends BasePresent<MainActivity> {
	public MainPresent(Activity activity, MainActivity view) {
		super(activity, view);
	}

	@Override
	public void onCreate() {
		init();
		super.onCreate();
	}

	@Override
	public void onDestory() {
		super.onDestory();
	}

	@Override
	public void onStop() {
		super.onStop();
		mView.closeFam();
	}

	private void init() {
		Bmob.initialize(mActivity, Config.BMOB_SERVER_APP_ID);
		OtherUtils.autoUpdate(Config.UPDATE_AUTO, mActivity);
		SDCard.detectionSDcard();
	}

	@Override
	public void onClick(View v) {
		// TODO: 2016/3/13 位置待定
		Class c = null;
		switch (v.getId()) {
			case R.id.fabAbout:
				c = AboutActivity.class;
				break;
			case R.id.fabFeedback:
				c = FeedBackActivity.class;
				break;
			case R.id.fabScan:
				c = ScanActivity.class;
				break;
			case R.id.rpPic:
				c = PictureActivity.class;
				break;
			case R.id.rpCustomContent:
				c = CustomActivity.class;
				break;
			case R.id.rpNetCard:
				c = NetCardActivity.class;
				break;
			case R.id.rpCard:
				c = CardActivity.class;
				break;
			case R.id.rpTxt:
				c = TextActivity.class;
				break;
			case R.id.rpUrl:
				c = UrlActivity.class;
				break;
			case R.id.rpSms:
				c = SmsActivity.class;
				break;
			case R.id.rpEmail:
				c = EmailActivity.class;
				break;
			case R.id.rpWifi:
				c = WifiActivity.class;
				break;
			case R.id.rpAudio:
				c = AudioActivity.class;
				break;
		}
		mView.toOtherActivity(c, null);
	}
}
