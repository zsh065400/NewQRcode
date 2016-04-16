package org.laosao.two.view;

import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.present.CustomPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.ICustomView;

import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class CustomActivity extends BaseActivity<CustomPresent>
		implements ICustomView {

	private MaterialEditText mEtContent;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public CustomPresent createPersent() {
        return new CustomPresent(this, this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_custom;
    }

    @Override
	public void initView() {
		mEtContent = (MaterialEditText) findViewById(R.id.etConent);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}


    @Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return mEtContent.getText().toString();
	}

	@Override
	public void setText(String s) {
		mEtContent.setText(s);
	}
}
