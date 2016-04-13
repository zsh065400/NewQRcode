package org.laosao.two.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.PicturePresent;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IPictureView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class PictureActivity extends BaseActivity<PicturePresent>
		implements IPictureView {


	private ImageView mImageView;
	private MaterialEditText mEtConent;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
	}

    @Override
    public BasePresent createPersent() {
        return new PicturePresent(this, this);
    }

    private MaterialDialog mDialog = null;

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
	public void showBitmap(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
	}

	@Override
	public void closeFam() {

	}

	@Override
	public void initView() {
		mImageView = (ImageView) findViewById(R.id.imgPreview);
		mEtConent = (MaterialEditText) findViewById(R.id.etConent);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mImageView.setOnClickListener(this);
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return mEtConent.getText().toString();
	}

	@Override
	public void reset() {
		mEtConent.setText(Config.EMPTY_STR);
		mImageView.setImageResource(R.mipmap.default_bac);
	}

}
