package org.laosao.two.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.laosao.two.R;
import org.laosao.two.present.AboutPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IAboutView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;
import material.view.fab.FloatingActionsMenu;

public class AboutActivity extends BaseActivity<AboutPresent> implements IAboutView {

	private TextView mTvVersion, mTvInfo;
	private FloatingActionButton mFabShareApp, mFabCheckUpdate;
	private FloatingActionsMenu mFamMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mPresent = new AboutPresent(this, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public void initView() {
		mTvVersion = (TextView) findViewById(R.id.tvVersion);
		mTvInfo = (TextView) findViewById(R.id.tvInfo);
		mFabShareApp = (FloatingActionButton) findViewById(R.id.fabShare);
		mFabCheckUpdate = (FloatingActionButton) findViewById(R.id.fabCheckUpdate);
		mFamMore = (FloatingActionsMenu) findViewById(R.id.famMore);
	}

	@Override
	public void setListener() {
		mTvVersion.setOnClickListener(this);
		mTvInfo.setOnClickListener(this);
		mFabShareApp.setOnClickListener(this);
		mFabCheckUpdate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
	}

	@Override
	public void setVersion(String version) {
		if (version != null)
			mTvInfo.setText(getString(R.string.current_version)
					+ version);
	}

	@Override
	public void closeFam() {
		if (mFamMore.isExpanded())
			mFamMore.collapse();
	}

	@Override
	public void showDialog() {
		final MaterialDialog dialog = new MaterialDialog(this);
		dialog.setTitle("温馨提示");
		dialog.setMessage("您即将访问我们的新浪微博");
		dialog.setPositiveButton("看看", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(
						Uri.parse("http://weibo.com/p/1006063174917162/home?from=page_100606&mod=TAB#place"));
				startActivity(intent);
			}
		});
		dialog.setNegativeButton("不看了", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
