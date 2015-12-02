package org.laosao.two.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.LayoutRipple;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nineoldandroids.view.ViewHelper;
import com.zbar.lib.CaptureActivity;

import org.laosao.two.Config;
import org.laosao.two.GuideActivity;
import org.laosao.two.R;
import org.laosao.two.utils.GeneralUtil;

import cn.bmob.v3.Bmob;


/**
 * Created by Scout.Z on 2015/8/12.
 */
public class MainActivity extends Activity implements View.OnClickListener {
	public static final int REQ_SPLASH = 10001;

	private LayoutRipple rpPic, rpCustomContent,
			rpNetCard, rpCard,
			rpTxt, rpUrl,
			rpSms, rpEmail,
			rpWifi, rpAudio;
	private ButtonFloat btnFeekback, btnScan;
	private FloatingActionsMenu btnMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Config.getReference(this, Config.KEY_SPLASH) == Config.CODE_ERROR) {
			Intent i = new Intent(this, GuideActivity.class);
			startActivityForResult(i, REQ_SPLASH);
		}

		setContentView(R.layout.activity_main);
		Config.setLayoutTransparentStatus(this, R.color.material);
		Bmob.initialize(this, Config.BMOB_SERVER_APP_ID);
		initView();
		Config.detectionSDcard();

		GeneralUtil.autoUpdate(Config.UPDATE_AUTO, this);
	}


	private void initView() {
		rpPic = (LayoutRipple) findViewById(R.id.rpPic);
		rpCustomContent = (LayoutRipple) findViewById(R.id.rpCustomContent);
		rpNetCard = (LayoutRipple) findViewById(R.id.rpNetCard);
		rpCard = (LayoutRipple) findViewById(R.id.rpCard);
		rpTxt = (LayoutRipple) findViewById(R.id.rpTxt);
		rpUrl = (LayoutRipple) findViewById(R.id.rpUrl);
		rpSms = (LayoutRipple) findViewById(R.id.rpSms);
		rpEmail = (LayoutRipple) findViewById(R.id.rpEmail);
		rpWifi = (LayoutRipple) findViewById(R.id.rpWifi);
		rpAudio = (LayoutRipple) findViewById(R.id.rpAudio);
		btnFeekback = (ButtonFloat) findViewById(R.id.btnFeekback);
		btnScan = (ButtonFloat) findViewById(R.id.btnScan);
		btnMore = (FloatingActionsMenu) findViewById(R.id.fam_main);

		rpPic.setOnClickListener(this);
		rpCustomContent.setOnClickListener(this);
		rpNetCard.setOnClickListener(this);
		rpCard.setOnClickListener(this);
		rpTxt.setOnClickListener(this);
		rpUrl.setOnClickListener(this);
		rpSms.setOnClickListener(this);
		rpEmail.setOnClickListener(this);
		rpWifi.setOnClickListener(this);
		rpAudio.setOnClickListener(this);
		btnFeekback.setOnClickListener(this);
		btnScan.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == GuideActivity.RES_SHOULD_FINISH && requestCode == REQ_SPLASH) {
			finish();
		}
	}


	@Override
	public void onClick(View v) {
		Intent i = null;
		Class c = null;
		switch (v.getId()) {
			case R.id.btnFeekback:
				c = FeedBackActivity.class;
				break;
			case R.id.btnScan:
				c = CaptureActivity.class;
				break;
			case R.id.rpPic:
				c = PictureActivity.class;
				break;
			case R.id.rpCustomContent:
				c = CustomContentActivity.class;
				break;
			case R.id.rpNetCard:
				c = NetCardActivity.class;
				break;
			case R.id.rpCard:
				c = GeneralCardActivity.class;
				break;
			case R.id.rpTxt:
				c = TxtActivity.class;
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
		i = new Intent(this, c);
		startActivity(i);
	}

	/**
	 * 按两次退出程序
	 */
	private long currentTime = 0;

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - currentTime) > 2000) {
			currentTime = System.currentTimeMillis();
			Config.showShortToast(this, R.string.exit_log);
		} else {
			this.finish();
		}
	}


	@Override
	protected void onStop() {
		super.onStop();
		if (btnMore.isExpanded()) {
			btnMore.collapse();
		}
	}

	private static final int RIPPLE_SPEED = 78;

	/**
	 * 设置动画
	 *
	 * @param layoutRipple 自定义控件
	 */
	private void setOriginRiple(final LayoutRipple layoutRipple) {

		layoutRipple.post(new Runnable() {

			@Override
			public void run() {
				View v = layoutRipple.getChildAt(0);
				layoutRipple.setxRippleOrigin(ViewHelper.getX(v)
						+ v.getWidth() / 2);
				layoutRipple.setyRippleOrigin(ViewHelper.getY(v)
						+ v.getHeight() / 2);
				layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));
				layoutRipple.setRippleSpeed(RIPPLE_SPEED);
			}
		});

	}


}
