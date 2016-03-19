package org.laosao.two.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.NetCardPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.INetCardView;

import java.util.ArrayList;
import java.util.List;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class NetCardActivity extends BaseActivity<NetCardPresent> implements INetCardView {

	private ImageView mImageView;
	private MaterialEditText mEtName, mEtQq, mEtWechat, mEtAddress,
			mEtCompany, mEtJob, mEtEmail, mEtPhone, mEtWeibo, mEtProFile;
	private FloatingActionButton mFabCreate;
	private List<MaterialEditText> mEtList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_card);
		mPresent = new NetCardPresent(this, this);
	}

	@Override
	public void initView() {
		mImageView = (ImageView) findViewById(R.id.imgPreview);
		mEtName = (MaterialEditText) findViewById(R.id.etName);
		mEtQq = (MaterialEditText) findViewById(R.id.etQq);
		mEtWechat = (MaterialEditText) findViewById(R.id.etWechat);
		mEtAddress = (MaterialEditText) findViewById(R.id.etAddress);
		mEtCompany = (MaterialEditText) findViewById(R.id.etCompany);
		mEtJob = (MaterialEditText) findViewById(R.id.etJob);
		mEtEmail = (MaterialEditText) findViewById(R.id.etEmail);
		mEtPhone = (MaterialEditText) findViewById(R.id.etPhone);
		mEtWeibo = (MaterialEditText) findViewById(R.id.etWeibo);
		mEtProFile = (MaterialEditText) findViewById(R.id.etProFile);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);

		mEtList = new ArrayList<>();
		mEtList.add(mEtName);
		mEtList.add(mEtQq);
		mEtList.add(mEtWechat);
		mEtList.add(mEtAddress);
		mEtList.add(mEtCompany);
		mEtList.add(mEtJob);
		mEtList.add(mEtEmail);
		mEtList.add(mEtPhone);
		mEtList.add(mEtWeibo);
		mEtList.add(mEtProFile);
	}

	@Override
	public void setListener() {
		mFabCreate.setOnClickListener(this);
		mImageView.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return mEtName.getText().toString();
	}

	private MaterialDialog mDialog = null;

	@Override
	public void reset() {
		for (MaterialEditText et : mEtList) {
			et.setText(Config.EMPTY_STR);
		}
		mImageView.setImageBitmap(null);
	}

	@Override
	public void showHeadImage(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
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
	public String[] getBean() {
		String[] sb = new String[10];
		for (int i = 0; i < sb.length; i++) {
			sb[i] = mEtList.get(i).getText().toString();
		}
		return sb;
	}
}

