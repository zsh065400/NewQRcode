package org.laosao.two.view;

import android.os.Bundle;
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
	private RippleView mRpPic, mRpCustom,
			mRpNetCard, mRpCard,
			mRpUrl,
			mRpSms, mRpEmail,
			mRpWifi, mRpAudio,
			mRpFile;
	private FloatingActionButton mFabFeedback, mFabScan, mFabAbout;
	private FloatingActionsMenu mFamMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPresent = new MainPresent(this, this);
		getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.STATE_IDLE);
	}

	public void initView() {
		mRpPic = (RippleView) findViewById(R.id.rpPic);
		mRpCustom = (RippleView) findViewById(R.id.rpCustomContent);
		mRpNetCard = (RippleView) findViewById(R.id.rpNetCard);
		mRpCard = (RippleView) findViewById(R.id.rpCard);
		mRpUrl = (RippleView) findViewById(R.id.rpUrl);
		mRpSms = (RippleView) findViewById(R.id.rpSms);
		mRpEmail = (RippleView) findViewById(R.id.rpEmail);
		mRpWifi = (RippleView) findViewById(R.id.rpWifi);
		mRpAudio = (RippleView) findViewById(R.id.rpAudio);
		mRpFile = (RippleView) findViewById(R.id.rpFile);
		mFabFeedback = (FloatingActionButton) findViewById(R.id.fabFeedback);
		mFabScan = (FloatingActionButton) findViewById(R.id.fabScan);
		mFabAbout = (FloatingActionButton) findViewById(R.id.fabAbout);
		mFamMore = (FloatingActionsMenu) findViewById(R.id.famMore);
	}

	@Override
	public void setListener() {
		mRpPic.setOnClickListener(this);
		mRpCustom.setOnClickListener(this);
		mRpNetCard.setOnClickListener(this);
		mRpCard.setOnClickListener(this);
		mRpUrl.setOnClickListener(this);
		mRpSms.setOnClickListener(this);
		mRpEmail.setOnClickListener(this);
		mRpWifi.setOnClickListener(this);
		mRpAudio.setOnClickListener(this);
		mRpFile.setOnClickListener(this);
		mFabFeedback.setOnClickListener(this);
		mFabScan.setOnClickListener(this);
		mFabAbout.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
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
		if (mFamMore.isExpanded()) {
			mFamMore.collapse();
		}
	}

}
