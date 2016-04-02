package org.laosao.two.present.scan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
			mView.showToast("检测到Wifi二维码，可点击右下角连接", Toast.LENGTH_LONG);
		} else if (mContent.startsWith("BEGIN:VCARD")) {
			mContent = parseCardData(mContent);
			mView.changeFabState(1);
			mView.showToast("检测到VCard名片，点击右下角按钮进入联系人界面", Toast.LENGTH_LONG);
		} else if (mContent.startsWith("PhoneNumber")) {
			mView.changeFabState(2);
			parseSmsData(mContent);
			mView.showToast("检测到短信二维码，点击右下角按钮进入短信界面", Toast.LENGTH_LONG);
		} else if (mContent.startsWith("EmailAddress")) {
			mView.changeFabState(2);
			parseEmailData(mContent);
			mView.showToast("检测到邮件二维码，点击右下角按钮进入邮件界面", Toast.LENGTH_LONG);
		}
		mView.setScanResult(mContent);
	}

	private String mPhoneNumber;
	private String mCott;
	private String mEmailAddress;
	private String mProject;

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
				insertToContacts();
				break;
			case sms:
				sendSms();
				break;
			case email:
				sendMail();
				break;
		}
	}

	private enum Type {
		normal, wifi, card, sms, email;
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

	private String mName;
	private String mPhone;
	private String mEmail;
	private String mOrg;
	private String mTitle;

	private String parseCardData(String s) {
		mType = Type.card;
		String[] c = s.split(Config.NEW_LINE);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < c.length; i++) {
			if (c[i].startsWith("N:") || c[i].startsWith("FN:")) {
				mName = c[i].split(":")[1];
				if (sb.toString().contains("姓名：")) {
					continue;
				}
				sb.append("姓名：").append(mName).append(Config.NEW_LINE);
			} else if (c[i].startsWith("TEL:")) {
				mPhone = c[i].split(":")[1];
				sb.append("电话：").append(mPhone).append(Config.NEW_LINE);
			} else if (c[i].startsWith("EMAIL:")) {
				mEmail = c[i].split(":")[1];
				sb.append("邮箱：").append(mEmail).append(Config.NEW_LINE);
			} else if (c[i].startsWith("ORG:")) {
				mOrg = c[i].split(":")[1];
				sb.append("公司：").append(mOrg).append(Config.NEW_LINE);
			} else if (c[i].startsWith("TITLE:")) {
				mTitle = c[i].split(":")[1];
				sb.append("职位：").append(mTitle).append(Config.NEW_LINE);
			}
		}
		return sb.toString();
	}

	private void parseSmsData(String s) {
		mType = Type.sms;
		String[] c = s.split(Config.NEW_LINE);
		mPhoneNumber = c[0].split("：")[1];
		mCott = c[1].split("：")[1];
	}

	private void parseEmailData(String s) {
		mType = Type.email;
		String[] c = s.split(Config.NEW_LINE);
		mEmailAddress = c[0].split("：")[1];
		mProject = c[1].split("：")[1];
		mCott = c[2].split("：")[1];
	}

	private void sendSms() {
		Uri uri = Uri.parse("smsto:" + mPhoneNumber);
		Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
		sendIntent.putExtra("sms_body", mCott);
		mActivity.startActivity(sendIntent);
	}

	public void sendMail() {
		String[] reciver = new String[]{mEmailAddress};
		String[] mySbuject = new String[]{mProject};
//		String myCc = "cc";
		Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
		myIntent.setType("plain/text");
		myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
//		myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);
		myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
		myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mCott);
		mActivity.startActivity(Intent.createChooser(myIntent, "选择发送"));
	}

	private void insertToContacts() {
		Intent it = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(
				Uri.parse("content://com.android.contacts"), "contacts"));
		it.setType("vnd.android.cursor.dir/person");
		// it.setType("vnd.android.cursor.dir/contact");
		// it.setType("vnd.android.cursor.dir/raw_contact");
		// 联系人姓名
		it.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, mName);
		// 公司
		it.putExtra(android.provider.ContactsContract.Intents.Insert.COMPANY,
				mOrg);
		// email
		it.putExtra(android.provider.ContactsContract.Intents.Insert.EMAIL,
				mEmail);
		// 手机号码
		it.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE,
				mPhone);
		// 备注信息
		it.putExtra(android.provider.ContactsContract.Intents.Insert.JOB_TITLE,
				mTitle);
		mActivity.startActivity(it);
	}
}
