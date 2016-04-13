package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.UrlPresent;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IUrlView;

import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class UrlActivity extends BaseActivity<UrlPresent>
		implements IUrlView {
	private MaterialEditText mEtUrl;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url);
	}

    @Override
    public BasePresent createPersent() {
        return new UrlPresent(this, this);
    }

    @Override
	public void reset() {
		mEtUrl.setText(Config.EMPTY_STR);
	}

	@Override
	public void initView() {
		mEtUrl = (MaterialEditText) findViewById(R.id.etWebUrl);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		String url = mEtUrl.getText().toString();
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		if (!url.startsWith("http://") || !url.startsWith("https://")) {
			return "Http://" + url;
		}
		return url;
	}
}
