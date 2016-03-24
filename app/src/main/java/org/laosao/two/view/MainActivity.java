package org.laosao.two.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.present.MainPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IMainView;

import material.view.RippleView;
import material.view.fab.FloatingActionButton;
import material.view.fab.FloatingActionsMenu;
import me.imid.swipebacklayout.lib.SwipeBackLayout;


/**
 * Created by Scout.Z on 2015/8/12.
 */
public class MainActivity extends BaseActivity<MainPresent> implements IMainView {
	private RippleView rpPic, rpCustom,
			rpNetCard, rpCard,
			rpTxt, rpUrl,
			rpSms, rpEmail,
			rpWifi, rpAudio;
	private FloatingActionButton fabFeedback, fabScan, fabAbout;
	private FloatingActionsMenu famMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPresent = new MainPresent(this, this);
		getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.STATE_IDLE);
	}

	public void initView() {
		rpPic = (RippleView) findViewById(R.id.rpPic);
		rpCustom = (RippleView) findViewById(R.id.rpCustomContent);
		rpNetCard = (RippleView) findViewById(R.id.rpNetCard);
		rpCard = (RippleView) findViewById(R.id.rpCard);
		rpTxt = (RippleView) findViewById(R.id.rpTxt);
		rpUrl = (RippleView) findViewById(R.id.rpUrl);
		rpSms = (RippleView) findViewById(R.id.rpSms);
		rpEmail = (RippleView) findViewById(R.id.rpEmail);
		rpWifi = (RippleView) findViewById(R.id.rpWifi);
		rpAudio = (RippleView) findViewById(R.id.rpAudio);
		fabFeedback = (FloatingActionButton) findViewById(R.id.fabFeedback);
		fabScan = (FloatingActionButton) findViewById(R.id.fabScan);
		fabAbout = (FloatingActionButton) findViewById(R.id.fabAbout);
		famMore = (FloatingActionsMenu) findViewById(R.id.famMore);
	}

	@Override
	public void setListener() {
		rpPic.setOnClickListener(this);
		rpCustom.setOnClickListener(this);
		rpNetCard.setOnClickListener(this);
		rpCard.setOnClickListener(this);
		rpTxt.setOnClickListener(this);
		rpUrl.setOnClickListener(this);
		rpSms.setOnClickListener(this);
		rpEmail.setOnClickListener(this);
		rpWifi.setOnClickListener(this);
		rpAudio.setOnClickListener(this);
		fabFeedback.setOnClickListener(this);
		fabScan.setOnClickListener(this);
		fabAbout.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
	}

	@Override
	public void onClick(View v) {
		mPresent.onClick(v);
	}


	@Override
	protected void onStop() {
		super.onStop();
		closeFam();
	}

	/**
	 * 按两次退出程序
	 */
	private long currentTime = 0;

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - currentTime) > 2000) {
			currentTime = System.currentTimeMillis();
			showToast("再按一次退出软件", Toast.LENGTH_SHORT);
		} else {
			this.finish();
		}
	}

	@Override
	public void closeFam() {
		if (famMore.isExpanded()) {
			famMore.collapse();
		}
	}

}
