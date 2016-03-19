package org.laosao.two.present.scan;

import android.app.Activity;
import android.content.Context;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.model.Config;
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
		if (mContent.startsWith(Config.KEY_SCAN_WIFI))
			mContent = parseWifiData(mContent);
		mView.setScanResult(mContent);
	}


	@Override
	public void onClick(View v) {
		// TODO: 2016/3/19 单独处理名片和wifi二维码
		ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
		clip.setText(mContent);
		mView.showToast(R.string.copy_to, Toast.LENGTH_SHORT);
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
	private Type type = Type.normal;

	private String parseWifiData(String s) {
		type = Type.wifi;
		String temp = s.replace("WIFI:", Config.EMPTY_STR);
		String[] c = temp.split(";");
		mSsid = c[1].replace("S:", SSID_START) + Config.NEW_LINE;
		mPassword = c[2].replace("P:", PW_START) + Config.NEW_LINE;
		mSecurity = c[0].replace("T:", SEC_START);
		return mSsid + mPassword + mSecurity;
	}
}
