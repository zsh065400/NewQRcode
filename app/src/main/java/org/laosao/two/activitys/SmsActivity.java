package org.laosao.two.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.T;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class SmsActivity extends BaseActivity {
	private MaterialEditText etSmsPhone;
	private MaterialEditText etSmsContent;
	private ButtonFloat btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}


	private void init() {
		etSmsPhone = (MaterialEditText) findViewById(R.id.etSmsPhone);
		etSmsContent = (MaterialEditText) findViewById(R.id.etSmsContent);
//		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String content = etSmsPhone.getText().toString() +
				                 Config.NEW_LINE +
				                 etSmsContent.getText().toString();
		if (TextUtils.isEmpty(content.replace(Config.NEW_LINE, Config.EMPTY_STR))) {
			T.showShortToast(this, getString(R.string.content_not_empty));
			return;
		}

		etSmsPhone.setText(Config.EMPTY_STR);
		etSmsContent.setText(Config.EMPTY_STR);

		startCreateActivity(content);
	}
}
