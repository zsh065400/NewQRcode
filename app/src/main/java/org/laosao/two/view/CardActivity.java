package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.present.CardPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.ICardView;

import java.util.ArrayList;
import java.util.List;

import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class CardActivity extends BaseActivity<CardPresent> implements
		ICardView {
	private MaterialEditText mEtName;
	private MaterialEditText mEtCompany;
	private MaterialEditText mEtJob;
	private MaterialEditText mEtPhone;
	private MaterialEditText mEtQq;
	private MaterialEditText mEtEmail;
	private MaterialEditText mEtPerson;
	private FloatingActionButton mFabCreate;
	List<MaterialEditText> mEditTexts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mPresent = new CardPresent(this, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
	}

	@Override
	public void initView() {
		mEtName = (MaterialEditText) findViewById(R.id.etName);
		mEtCompany = (MaterialEditText) findViewById(R.id.etCompany);
		mEtJob = (MaterialEditText) findViewById(R.id.etJob);
		mEtPhone = (MaterialEditText) findViewById(R.id.etPhone);
		mEtQq = (MaterialEditText) findViewById(R.id.etQq);
		mEtEmail = (MaterialEditText) findViewById(R.id.etEmail);
		mEtPerson = (MaterialEditText) findViewById(R.id.etPerson);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);

		mEditTexts = new ArrayList<>(8);
		mEditTexts.add(mEtName);
		mEditTexts.add(mEtCompany);
		mEditTexts.add(mEtJob);
		mEditTexts.add(mEtPhone);
		mEditTexts.add(mEtQq);
		mEditTexts.add(mEtEmail);
		mEditTexts.add(mEtPerson);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		String name = mEtName.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return null;
		}
		for (MaterialEditText et : mEditTexts) {
			String s = et.getText().toString();
			if (!TextUtils.isEmpty(s)) {
				sb.append(s + "\n");
			}
		}
		return sb.toString();
	}

	@Override
	public void reset() {
		for (MaterialEditText et : mEditTexts) {
			et.setText(Config.EMPTY_STR);
		}
	}
}
