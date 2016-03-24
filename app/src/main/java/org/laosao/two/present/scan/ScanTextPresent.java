package org.laosao.two.present.scan;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.WifiConnect;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.scan.ScanTextActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanTextPresent extends BasePresent<ScanTextActivity> {
	public ScanTextPresent(Activity activity, ScanTextActivity view) {
		super(activity, view);
	}

	private String mContent;

	@Override
	public void onCreate() {
		super.onCreate();
		mContent = mView.getContent();
		if (mContent.startsWith(Config.KEY_SCAN_WIFI)) {
			mContent = parseWifiData(mContent);
			mView.changeFabState(1);
			mView.showToast("检测到Wifi二维码，可点击右下角连接", Toast.LENGTH_SHORT);
		}
		mView.setScanResult(mContent);
	}


	@Override
	public void onClick(View v) {
		switch (mType) {
			case normal:
				ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
				clip.setText(mContent);
				mView.showToast(R.string.copy_to, Toast.LENGTH_SHORT);
				break;
			case wifi:
				mView.showToast("正在连接", Toast.LENGTH_SHORT);
				WifiConnect wc = new WifiConnect((WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE));
				switch (mSecurity) {
					case "WPA/WPA2":
						boolean connect = wc.Connect(mSsid, mPassword, WifiConnect.WifiCipherType.WIFICIPHER_WPA);
						if (connect) {
							mView.showToast("连接成功", Toast.LENGTH_SHORT);
						} else {
							mView.showToast("连接失败", Toast.LENGTH_SHORT);
						}
						break;

					case "WEP":
						wc.Connect(mSsid, mPassword, WifiConnect.WifiCipherType.WIFICIPHER_WEP);
						break;

					case "无加密":
						wc.Connect(mSsid, mPassword, WifiConnect.WifiCipherType.WIFICIPHER_NOPASS);
						break;
				}
				break;

			case card:
				mView.changeFabState(1);
				break;
		}
	}

	private enum Type {
		normal, wifi, card;
	}

	private String mSsid;
	private String mPassword;
	private String mSecurity;

	private static final String SSID_START = "接入点SSID：";
	private static final String PW_START = "密码：";
	private static final String SEC_START = "加密方式：";
	private static Type mType = Type.normal;

	private String parseWifiData(String s) {
		mType = Type.wifi;
		String temp = s.replace("WIFI:", Config.EMPTY_STR);
		String[] c = temp.split(";");
		mSsid = c[1].replace("S:", SSID_START) + Config.NEW_LINE;
		mPassword = c[2].replace("P:", PW_START) + Config.NEW_LINE;
		mSecurity = c[0].replace("T:", SEC_START);
		return mSsid + mPassword + mSecurity;
	}
}
