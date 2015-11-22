package org.laosao.two.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;

public class WifiActivity extends BaseActivity {
	
	private MaterialEditText etWifiName;
	private MaterialEditText etWifiPw;
	private Spinner snWifiSecurity;
	private ButtonFloat btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
	}

	private void initView() {
		etWifiName = (MaterialEditText) findViewById(R.id.etWifiName);
		etWifiPw = (MaterialEditText) findViewById(R.id.etWifiPw);
		snWifiSecurity = (Spinner) findViewById(R.id.snWifiSecurity);
		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);

		btnCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String wifiName = etWifiName.getText().toString();
		String wifiPw = etWifiPw.getText().toString();
		String wifiSecurity = (String) snWifiSecurity.getSelectedItem();
		if (!TextUtils.isEmpty(wifiName) && !TextUtils.isEmpty(wifiPw)) {
			String content = "WIFI:" + "T:" + wifiSecurity + ";" +
							 "S:" + wifiName + ";" +
							 "P:" + wifiPw + ";";
			startCreateActivity(content);
			etWifiName.setText(Config.EMPTY_STR);
			etWifiPw.setText(Config.EMPTY_STR);
		} else {
			Toast.makeText(this, "无线内容不能为空", Toast.LENGTH_SHORT).show();
		}
	}
}
