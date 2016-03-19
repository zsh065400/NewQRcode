package org.laosao.two.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.LayoutRipple;

import org.laosao.two.R;
import org.laosao.two.present.MainPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IMainView;

import material.view.fab.FloatingActionButton;
import material.view.fab.FloatingActionsMenu;


/**
 * Created by Scout.Z on 2015/8/12.
 */
public class MainActivity extends BaseActivity<MainPresent> implements IMainView {
	private LayoutRipple rpPic, rpCustom,
			rpNetCard, rpCard,
			rpTxt, rpUrl,
			rpSms, rpEmail,
			rpWifi, rpAudio;
	private FloatingActionButton fabFeedback, fabScan, fabAbout;
	private FloatingActionsMenu famMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		flag = 0;
		mPresent = new MainPresent(this, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void initView() {
		rpPic = (LayoutRipple) findViewById(R.id.rpPic);
		rpCustom = (LayoutRipple) findViewById(R.id.rpCustomContent);
		rpNetCard = (LayoutRipple) findViewById(R.id.rpNetCard);
		rpCard = (LayoutRipple) findViewById(R.id.rpCard);
		rpTxt = (LayoutRipple) findViewById(R.id.rpTxt);
		rpUrl = (LayoutRipple) findViewById(R.id.rpUrl);
		rpSms = (LayoutRipple) findViewById(R.id.rpSms);
		rpEmail = (LayoutRipple) findViewById(R.id.rpEmail);
		rpWifi = (LayoutRipple) findViewById(R.id.rpWifi);
		rpAudio = (LayoutRipple) findViewById(R.id.rpAudio);
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
