package org.laosao.two.present;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
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
		super.onCreate();
		init();
	}

	@Override
	public void onStop() {
		super.onStop();
		mView.closeFam();
		mHandler.removeCallbacks(mRunnable);
	}

	private void init() {
		Bmob.initialize(mActivity, Config.BMOB_SERVER_APP_ID);
		OtherUtils.autoUpdate(Config.UPDATE_AUTO, mActivity);
		SDCard.detectionSDcard();

		mHandler = new DelayedHandler();
		mRunnable = new Runnable() {
			@Override
			public void run() {
				mView.toOtherActivity(mTarget, null);
			}
		};
	}

	@Override
	public void onDestory() {
		super.onDestory();
		mHandler = null;
		mRunnable = null;
		mTarget = null;
	}

	private DelayedHandler mHandler;
	private Runnable mRunnable;
	private Class mTarget = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fabAbout:
				mTarget = AboutActivity.class;
				break;
			case R.id.fabFeedback:
				mTarget = FeedBackActivity.class;
				break;
			case R.id.fabScan:
				mTarget = ScanActivity.class;
				break;
			case R.id.rpPic:
				mTarget = PictureActivity.class;
				break;
			case R.id.rpCustomContent:
				mTarget = CustomActivity.class;
				break;
			case R.id.rpNetCard:
				mTarget = NetCardActivity.class;
				break;
			case R.id.rpCard:
				mTarget = CardActivity.class;
				break;
			case R.id.rpTxt:
				mTarget = TextActivity.class;
				break;
			case R.id.rpUrl:
				mTarget = UrlActivity.class;
				break;
			case R.id.rpSms:
				mTarget = SmsActivity.class;
				break;
			case R.id.rpEmail:
				mTarget = EmailActivity.class;
				break;
			case R.id.rpWifi:
				mTarget = WifiActivity.class;
				break;
			case R.id.rpAudio:
				mTarget = AudioActivity.class;
				break;
		}
		mHandler.postDelayed(mRunnable, 375);
	}

	class DelayedHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

		}
	}
}
