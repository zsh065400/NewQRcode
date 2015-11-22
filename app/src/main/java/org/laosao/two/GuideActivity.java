package org.laosao.two;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFloat;

/**
 * Created by Scout.Z on 2015/8/12.
 * 引导页面，用于执行跳转或者引导工作
 */
public class GuideActivity extends Activity implements View.OnClickListener {
	private ButtonFloat btnGO;

	public static final int RES_SHOULD_FINISH = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}

	private void init() {
		btnGO = (ButtonFloat) findViewById(R.id.btnGo);
		btnGO.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnGo:
				Config.addReference(this, Config.KEY_SPLASH, Config.CODE_YES);
				finish();
				break;

		}
	}

}
