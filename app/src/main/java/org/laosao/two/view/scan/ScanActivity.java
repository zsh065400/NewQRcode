package org.laosao.two.view.scan;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.zbar.lib.CaptureActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanActivity extends CaptureActivity {

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}

	@Override
	protected void decodeSuccess(String result) {

	}

	@Override
	protected void decodeFail() {

	}
}