package org.laosao.two.view.scan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.scan.ScanNetCardPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.scan.IScanNetCardView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class ScanNetCardActivity extends BaseActivity<ScanNetCardPresent>
		implements IScanNetCardView {

	private ImageView mImgPreview;
	private MaterialEditText mEtName;
	private MaterialEditText mEtQq;
	private MaterialEditText mEtWechat;
	private MaterialEditText mEtAddress;
	private MaterialEditText mEtCompany;
	private MaterialEditText mText;
	private MaterialEditText mEtEmail;
	private MaterialEditText mEtPhone;
	private MaterialEditText mEtWeibo;
	private MaterialEditText mEtPerson;

	private FloatingActionButton mFabSave;

	private MaterialDialog mDialog;
	private String mUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_activity_net_card);
		mUrl = getIntent().getStringExtra(Config.KEY_RESULT);
		mPresent = new ScanNetCardPresent(this, this);
	}


	@Override
	public void initView() {
		mImgPreview = (ImageView) findViewById(R.id.imgPreview);
		mEtName = (MaterialEditText) findViewById(R.id.etName);
		mEtQq = (MaterialEditText) findViewById(R.id.etQq);
		mEtWechat = (MaterialEditText) findViewById(R.id.etWechat);
		mEtAddress = (MaterialEditText) findViewById(R.id.etAddress);
		mEtCompany = (MaterialEditText) findViewById(R.id.etCompany);
		mText = (MaterialEditText) findViewById(R.id.etJob);
		mEtEmail = (MaterialEditText) findViewById(R.id.etEmail);
		mEtPhone = (MaterialEditText) findViewById(R.id.etPhone);
		mEtWeibo = (MaterialEditText) findViewById(R.id.etWeibo);
		mEtPerson = (MaterialEditText) findViewById(R.id.etProFile);
		mFabSave = (FloatingActionButton) findViewById(R.id.fabSave);
	}

	@Override
	public void setListener() {
		mEtPerson.setOnClickListener(this);
		mFabSave.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return mUrl;
	}

	@Override
	public void setContent(PersonIdCard p) {
		p.getHead().loadImage(this, mImgPreview);
		mEtName.setText(p.getName());
		mEtQq.setText(p.getQq());
		mEtWechat.setText(p.getWechat());
		mEtAddress.setText(p.getAddress());
		mEtCompany.setText(p.getCompany());
		mText.setText(p.getJob());
		mEtEmail.setText(p.getE_email());
		mEtPhone.setText(p.getPhone());
		mEtWeibo.setText(p.getWeibo());
		mEtPerson.setText(p.getPerson());
	}

	@Override
	public void showWaitDialog() {
		mDialog = OtherUtils.showWaitDialog(this);
	}

	@Override
	public void dismissWaitDialog() {
		if (mDialog != null)
			mDialog.dismiss();
	}

	@Override
	public View getView() {
		return null;
	}

	@Override
	public void showDialog(String msg) {
		MaterialDialog dialog = new MaterialDialog(this);
		dialog.setMessage(msg);
		dialog.show();
	}

	@Override
	public String getIntroduce() {
		return mEtPerson.getText().toString();
	}
}
