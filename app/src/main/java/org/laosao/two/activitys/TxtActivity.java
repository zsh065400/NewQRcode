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
public class TxtActivity extends BaseActivity {
	private MaterialEditText etTextTitle;
	private MaterialEditText etTextContent;
	private ButtonFloat btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}


	private void init() {
		etTextTitle = (MaterialEditText) findViewById(R.id.etTextTitle);
		etTextContent = (MaterialEditText) findViewById(R.id.etTextContent);
//		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String content = null;
		String textTitle = etTextTitle.getText().toString();
		String textContent = etTextContent.getText().toString();
		if (TextUtils.isEmpty(textTitle) || TextUtils.isEmpty(textContent)) {
			T.showShortToast(this, getString(R.string.content_not_empty));
			return;
		}
		content = textTitle + Config.NEW_LINE + textContent;
//		if (content.startsWith(Config.NEW_LINE) || content.endsWith(Config.NEW_LINE)) {
//			content = content.replace(Config.NEW_LINE, Config.EMPTY_STR);
//		}

		etTextTitle.setText(Config.EMPTY_STR);
		etTextContent.setText(Config.EMPTY_STR);

		startCreateActivity(content);
	}
}
