package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.TextPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.ITextView;

import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class TextActivity extends BaseActivity<TextPresent>
		implements ITextView {
	private MaterialEditText mEtTitle;
	private MaterialEditText mEtContent;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		mPresent = new TextPresent(this, this);
	}

	@Override
	public void initView() {
		mEtTitle = (MaterialEditText) findViewById(R.id.etTextTitle);
		mEtContent = (MaterialEditText) findViewById(R.id.etTextContent);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		String title = mEtTitle.getText().toString();
		String content = mEtContent.getText().toString();
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
			return null;
		}
		return "标题：" + title +
				Config.NEW_LINE +
				"内容：" + content;
	}

	@Override
	public void reset() {
		mEtContent.setText(Config.EMPTY_STR);
		mEtTitle.setText(Config.EMPTY_STR);
	}
}
