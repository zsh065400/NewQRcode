package org.laosao.two.activitys;

import com.zbar.lib.CaptureActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanActivity extends CaptureActivity {
	@Override
	protected void decodeSuccess(String result) {
		// TODO: 2016/3/2 处理扫描结果
	}

	@Override
	protected void decodeFail() {

	}
}
