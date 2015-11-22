package org.laosao.two.activitys.result;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.T;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class TextResultActivity extends BaseActivity {
	private TextView tvResultContent;
	private ButtonFloat btnCopy;
	private String result;
	private String ssid;
	private String password;
	private String security;
	//	private Type type = Type.normal;

	private enum Type {
		normal, wifi;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_text);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
		result = getIntent().getStringExtra(Config.KEY_RESULT);

		if (result.startsWith(Config.KEY_SCAN_WIFI))
			result = parseWifiData(result);

		tvResultContent.setText(result);
	}

	private String parseWifiData(String s) {
		String temp = s.replace("WIFI:", Config.EMPTY_STR);
		String[] c = temp.split(";");
		ssid = c[1].replace("S:", SSID_START) + Config.NEW_LINE;
		password = c[2].replace("P:", PW_START) + Config.NEW_LINE;
		security = c[0].replace("T:", SEC_START);
		//		type = Type.wifi;
		return ssid + password + security;
	}

	private void initView() {
		tvResultContent = (TextView) findViewById(R.id.tvResultContent);
		btnCopy = (ButtonFloat) findViewById(R.id.btnCopy);
		btnCopy.setOnClickListener(this);
	}

	private static final String SSID_START = "接入点SSID：";
	private static final String PW_START = "密码：";
	private static final String SEC_START = "加密方式：";


	public void init() {
		System.out.println("罗春雨");
	}


	@Override
	public void onClick(View v) {
		//		if (type == Type.wifi) {
		//			String tSsid = ssid.replace(SSID_START, Config.EMPTY_STR).
		//																	 replace(Config.NEW_LINE,
		//																			Config.EMPTY_STR);
		//			String tPassword = password.replace(PW_START, Config.EMPTY_STR).
		//																		   replace(Config.NEW_LINE,
		//																				  Config.EMPTY_STR);
		//			String tSecurity = security.replace(SEC_START, Config.EMPTY_STR);
		//			L.outputDebug(tSsid + ";" + tPassword + ";" + tSecurity);
		//
		//			Wifi wifi = new Wifi((WifiManager) getSystemService(WIFI_SERVICE));
		//			boolean res = false;
		//			if (tSecurity.equals("WPA/WPA2")) {
		//				res = wifi.Connect(tSsid, tPassword, Wifi.WifiCipherType.WIFICIPHER_WPA);
		//			} else if (tSecurity.equals("WEP")) {
		//				res = wifi.Connect(tSsid, tPassword, Wifi.WifiCipherType.WIFICIPHER_WEP);
		//			} else if (tSecurity.equals("无加密")) {
		//				res = wifi.Connect(tSsid, tPassword, Wifi.WifiCipherType.WIFICIPHER_NOPASS);
		//			}
		//
		//			if (res) {
		//				Toast.makeText(this, "连接成功", Toast.LENGTH_SHORT).show();
		//			} else {
		//				Toast.makeText(this, "连接失败，请检查并重试", Toast.LENGTH_SHORT).show();
		//			}
		//		} else {
		ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clip.setText(result);
		T.showShortToast(this, getString(R.string.copy_to));
		//	}
	}
}
