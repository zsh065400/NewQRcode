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
public class EmailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}

	private MaterialEditText etEmail;
	private MaterialEditText etEmailTheme;
	private MaterialEditText etEmailContent;
	private ButtonFloat btnCreate;

	private void init() {
		etEmail = (MaterialEditText) findViewById(R.id.etEmail);
		etEmailTheme = (MaterialEditText) findViewById(R.id.etEmailTheme);
		etEmailContent = (MaterialEditText) findViewById(R.id.etEmailContent);
		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String content = etEmail.getText().toString() +
				                 Config.NEW_LINE +
				                 etEmailTheme.getText().toString() +
				                 Config.NEW_LINE +
				                 etEmailContent.getText().toString();
		if (TextUtils.isEmpty(content.replace(Config.NEW_LINE, Config.EMPTY_STR))) {
			T.showShortToast(this, getString(R.string.content_not_empty));
			return;
		}

		etEmail.setText(Config.EMPTY_STR);
		etEmailContent.setText(Config.EMPTY_STR);
		etEmailTheme.setText(Config.EMPTY_STR);

		startCreateActivity(content);
	}
}
