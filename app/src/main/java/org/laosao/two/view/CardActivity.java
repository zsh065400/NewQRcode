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
	private MaterialEditText mEtEmail;
	private FloatingActionButton mFabCreate;
	List<MaterialEditText> mEditTexts;
	List<String> mGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		mPresent = new CardPresent(this, this);
	}

	@Override
	public void initView() {
		mEtName = (MaterialEditText) findViewById(R.id.etName);
		mEtCompany = (MaterialEditText) findViewById(R.id.etCompany);
		mEtJob = (MaterialEditText) findViewById(R.id.etJob);
		mEtPhone = (MaterialEditText) findViewById(R.id.etPhone);
		mEtEmail = (MaterialEditText) findViewById(R.id.etEmail);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);

		mEditTexts = new ArrayList<>(5);
		mEditTexts.add(mEtName);
		mEditTexts.add(mEtCompany);
		mEditTexts.add(mEtJob);
		mEditTexts.add(mEtPhone);
		mEditTexts.add(mEtEmail);
		mGo = new ArrayList<>(5);
		mGo.add("N:");
		mGo.add("ORG:");
		mGo.add("TITLE:");
		mGo.add("TEL:");
		mGo.add("EMAIL:");
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("BEGIN:VCARD").append(Config.NEW_LINE);
		sb.append("VERSION:3.0").append(Config.NEW_LINE);
		int i = 0;
		for (MaterialEditText et : mEditTexts) {
			String s = et.getText().toString();
			if (TextUtils.isEmpty(s)) {
				return null;
			} else {
				sb.append(mGo.get(i)).append(et.getText().toString()).append(Config.NEW_LINE);
				if (i == 0) {
					sb.append("FN:").append(et.getText().toString()).append(Config.NEW_LINE);
				}
			}
			i++;
		}
		sb.append("END:VCARD").append(Config.NEW_LINE);
		return sb.toString();
	}

	@Override
	public void reset() {
		for (MaterialEditText et : mEditTexts) {
			et.setText(Config.EMPTY_STR);
		}
	}

}
