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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class GeneralCardActivity extends BaseActivity {
	private MaterialEditText etName;
	private MaterialEditText etCompany;
	private MaterialEditText etJob;
	private MaterialEditText etPhone;
	private MaterialEditText etQq;
	private MaterialEditText etEmail;
	private MaterialEditText etPerson;
	private ButtonFloat btnCreate;
	private List<MaterialEditText> value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}


	private void init() {
		etName = (MaterialEditText) findViewById(R.id.etName);
		etCompany = (MaterialEditText) findViewById(R.id.etCompany);
		etJob = (MaterialEditText) findViewById(R.id.etJob);
		etPhone = (MaterialEditText) findViewById(R.id.etPhone);
		etQq = (MaterialEditText) findViewById(R.id.etQq);
		etEmail = (MaterialEditText) findViewById(R.id.etEmail);
		etPerson = (MaterialEditText) findViewById(R.id.etPerson);
		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);
		value = new ArrayList<>();

		value.add(etCompany);
		value.add(etJob);
		value.add(etPhone);
		value.add(etQq);
		value.add(etEmail);
		value.add(etPerson);

	}

	@Override
	public void onClick(View v) {
		StringBuffer sb = new StringBuffer();
		if (TextUtils.isEmpty(etName.getText())) {
			T.showShortToast(this, getString(R.string.name_not_empty));
			return;
		}
		sb.append(etName.getText().toString());
		sb.append(Config.NEW_LINE);
		for (int i = 0; i < value.size(); i++) {
			String temp = value.get(i).getText().toString();
			if (!TextUtils.isEmpty(temp)) {
				sb.append(temp);
				sb.append(Config.NEW_LINE);
			}
		}

		for (int i = 0; i < value.size(); i++) {
			value.get(i).setText(Config.EMPTY_STR);
		}
		etName.setText(Config.EMPTY_STR);
		startCreateActivity(sb.toString());
	}
}
