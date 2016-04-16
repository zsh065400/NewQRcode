package org.laosao.two.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import org.laosao.two.R;
import org.laosao.two.present.PatronPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IPatronView;

import material.view.fab.FloatingActionButton;

public class PatronActivity extends BaseActivity<PatronPresent>
		implements IPatronView {

	private FloatingActionButton mFabSave;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public PatronPresent createPersent() {
        return new PatronPresent(this, this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_patron;
    }

    @Override
	public void initView() {
		mFabSave = (FloatingActionButton) findViewById(R.id.fabSave);
	}

	@Override
	public void setListener() {
		mFabSave.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
	}

	@Override
	public Bitmap getZfb() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zfb);
		return bitmap;
	}
}
