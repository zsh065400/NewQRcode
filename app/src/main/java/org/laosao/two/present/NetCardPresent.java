package org.laosao.two.present;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.Config;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.NetCardActivity;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import material.dialog.MaterialDialog;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class NetCardPresent extends BasePresent<NetCardActivity> {

	private String mPhotoPath = null;
	private String mTempPath = null;
	private String mUrl = null;
	private Bitmap mBitmap;

	public NetCardPresent(Activity activity, NetCardActivity view) {
		super(activity, view);
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public String getUrl() {
		return mUrl;
	}

	private void openCamera() {
		mPhotoPath = SDCard.osCamera +
				File.separator +
				OtherUtils.getCurrentTime() +
				Config.SUFFIX_PNG;
		ImageUtils.openCamera(mActivity, mPhotoPath);
	}

	private void cropBitmap() {
		mTempPath = SDCard.cameraDir + File.separator + "剪裁" + OtherUtils.getCurrentTime() + Config.SUFFIX_PNG;
		ImageUtils.cropImage(Uri.fromFile(new File(mPhotoPath)), mActivity, mTempPath, 180, 320);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fabCreate:
				if (mView.getContent() == null || mTempPath == null) {
					mView.showToast(R.string.no_head, Toast.LENGTH_SHORT);
					return;
				}
				mView.showWaitDialog();
				BmobControl.newUploadImage(mActivity, mTempPath, new UploadCallback());
				break;
			case R.id.imgPreview:
				final MaterialDialog dialog = new MaterialDialog(mActivity);
				dialog.setTitle("一个提示");
				dialog.setMessage("请选择头像");
				dialog.setPositiveButton("图库", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ImageUtils.openGallery(mActivity);
						dialog.dismiss();
					}
				});
				dialog.setNegativeButton("相机", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						openCamera();
						dialog.dismiss();
					}
				});
				dialog.show();
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == mActivity.RESULT_OK) {
			switch (requestCode) {
				case Config.REQ_OPEN_IMG:
					mPhotoPath = ImageUtils.getPath(mActivity, data.getData());
					cropBitmap();
					break;
				case Config.REQ_OPEN_CAMERA:
					cropBitmap();
					break;
				case Config.REQ_CROP_IMG:
					mBitmap = BitmapFactory.decodeFile(mTempPath);
					mView.showHeadImage(mBitmap);
					break;
			}

		}
	}

	public class SaveCallback implements BmobControl.BmobSaveCallback {
		@Override
		public void onSuccess() {
			mView.reset();
			mView.dismissWaitDialog();
			mView.create(mUrl);
			recycle();
		}

		@Override
		public void onFail(String error) {
			mView.showToast(error, Toast.LENGTH_SHORT);
			mView.dismissWaitDialog();
		}
	}

	private void recycle() {
		mTempPath = null;
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
		}
		mPhotoPath = null;
		mTempPath = null;
		mUrl = null;
	}


	public class UploadCallback implements BmobControl.BmobUploadCallback {
		@Override
		public void onSuccess(String url, BmobFile img) {
			mUrl = Config.KEY_SCAN_NET_CARD + url;
			String[] bean = mView.getBean();
			PersonIdCard card = new PersonIdCard(bean[0],
					img,
					bean[1],
					bean[2],
					bean[3],
					bean[4],
					bean[5],
					bean[6],
					bean[7],
					bean[8],
					bean[9],
					url);
			BmobControl.insertObject(mActivity, new SaveCallback(), card);
		}

		@Override
		public void onFail(String error) {
			mView.dismissWaitDialog();
			mView.showToast(error, Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onDestory() {
		super.onDestory();
		recycle();
	}
}
