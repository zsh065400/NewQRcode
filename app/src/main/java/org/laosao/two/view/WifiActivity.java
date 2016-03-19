package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Spinner;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.WifiPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IWifiView;

import material.view.fab.FloatingActionButton;

public class WifiActivity extends BaseActivity<WifiPresent>
		implements IWifiView {

	private MaterialEditText mEtWifiName;
	private MaterialEditText mEtWifiPw;
	private Spinner mSnWifiSecurity;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mPresent = new WifiPresent(this, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
	}

	@Override
	public void initView() {
		mEtWifiName = (MaterialEditText) findViewById(R.id.etWifiName);
		mEtWifiPw = (MaterialEditText) findViewById(R.id.etWifiPw);
		mSnWifiSecurity = (Spinner) findViewById(R.id.snWifiSecurity);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		String wifiName = mEtWifiName.getText().toString();
		String wifiPw = mEtWifiPw.getText().toString();
		String wifiSecurity = (String) mSnWifiSecurity.getSelectedItem();
		if (!TextUtils.isEmpty(wifiName) && !TextUtils.isEmpty(wifiPw)) {
			String content = "WIFI:" + "T:" + wifiSecurity + ";" +
					"S:" + wifiName + ";" +
					"P:" + wifiPw + ";";
			return content;
		} else {
			return null;
		}
	}

	@Override
	public void reset() {
		mEtWifiName.setText(Config.EMPTY_STR);
		mEtWifiPw.setText(Config.EMPTY_STR);
	}
}
