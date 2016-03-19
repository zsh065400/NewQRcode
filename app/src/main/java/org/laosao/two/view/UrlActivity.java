package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.UrlPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IUrlView;

import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class UrlActivity extends BaseActivity<UrlPresent>
		implements IUrlView {
	private MaterialEditText mEtTitle;
	private MaterialEditText mEtUrl;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url);
		mPresent = new UrlPresent(this, this);
	}

	@Override
	public void reset() {
		mEtTitle.setText(Config.EMPTY_STR);
		mEtUrl.setText(Config.EMPTY_STR);
	}

	@Override
	public void initView() {
		mEtTitle = (MaterialEditText) findViewById(R.id.etWebTitle);
		mEtUrl = (MaterialEditText) findViewById(R.id.etWebUrl);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		String title = mEtTitle.getText().toString();
		String url = mEtUrl.getText().toString();
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url)) {
			return null;
		}
		return "标题：" + title +
				Config.NEW_LINE +
				"网址：" + url;
	}
}
