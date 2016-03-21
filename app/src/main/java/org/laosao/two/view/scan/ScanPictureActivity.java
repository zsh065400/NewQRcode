package org.laosao.two.view.scan;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.scan.ScanPicturePresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.scan.IScanPictureView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;
import material.view.fab.FloatingActionsMenu;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class ScanPictureActivity extends BaseActivity<ScanPicturePresent>
		implements IScanPictureView {

	private ImageView mImageView;
	private MaterialEditText mEtConent;
	private FloatingActionButton mFabSave;
	private FloatingActionButton mFabShare;
	private FloatingActionsMenu mFamMore;

	private MaterialDialog mDialog;

	private String mResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		mResult = getIntent().getStringExtra(Config.KEY_RESULT);
		mPresent = new ScanPicturePresent(this, this);
	}

	@Override
	public void initView() {
		mImageView = (ImageView) findViewById(R.id.imgPreview);
		mEtConent = (MaterialEditText) findViewById(R.id.etConent);
		mFabSave = (FloatingActionButton) findViewById(R.id.fabSave);
		mFabShare = (FloatingActionButton) findViewById(R.id.fabShare);
		mFamMore = (FloatingActionsMenu) findViewById(R.id.famMore);
	}

	@Override
	public void setListener() {
		mFabSave.setOnClickListener(this);
		mFabShare.setOnClickListener(this);
		mEtConent.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return mResult;
	}

	@Override
	public void showWaitDialog() {
		mDialog = OtherUtils.showWaitDialog(this);
	}

	@Override
	public void showBitmap(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
	}

	@Override
	public void dismissWaitDialog() {
		if (mDialog != null)
			mDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		mDialog = null;
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		closeFam();
	}

	@Override
	public void closeFam() {
		if (mFamMore.isExpanded()) {
			mFamMore.collapse();
		}
	}

	@Override
	public String getSendWord() {
		return mEtConent.getText().toString();
	}

	@Override
	public void showSendWordDialog(String text) {
		MaterialDialog dialog = new MaterialDialog(ScanPictureActivity.this);
		dialog.setMessage(text);
		dialog.show();
	}

	@Override
	public void setSendWord(String text) {
		mEtConent.setText(text);
	}
}
