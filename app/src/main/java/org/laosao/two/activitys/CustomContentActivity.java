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
public class CustomContentActivity extends BaseActivity {

	private MaterialEditText etContent;
	private ButtonFloat btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}

	private void init() {
		etContent = (MaterialEditText) findViewById(R.id.etConent);
		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String content = etContent.getText().toString();
		if (TextUtils.isEmpty(content)) {
			T.showShortToast(this, getString(R.string.content_not_empty));
			return;
		}
		etContent.setText(Config.EMPTY_STR);
		startCreateActivity(content);
	}
}
