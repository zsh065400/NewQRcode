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
public class UrlActivity extends BaseActivity {
	private MaterialEditText etWebTitle;
	private MaterialEditText etWebUrl;
	private ButtonFloat btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}


	private void init() {
		etWebTitle = (MaterialEditText) findViewById(R.id.etWebTitle);
		etWebUrl = (MaterialEditText) findViewById(R.id.etWebUrl);
		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String content = null;
		String webTitle = etWebTitle.getText().toString();
		String webUrl = etWebUrl.getText().toString();
		if (TextUtils.isEmpty(webUrl)) {
			T.showShortToast(this, getString(R.string.url_not_empty));
			return;
		}
		content = webTitle + Config.NEW_LINE + webUrl;
		if (content.startsWith(Config.NEW_LINE)) {
			content = content.replace(Config.NEW_LINE, Config.EMPTY_STR);
		}

		etWebTitle.setText(Config.EMPTY_STR);
		etWebUrl.setText(Config.EMPTY_STR);

		startCreateActivity(content);
	}
}
