package org.laosao.two.present;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.BitmapBmob;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.Config;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.PictureActivity;

import java.io.File;
import java.io.FileOutputStream;

import cn.bmob.v3.datatype.BmobFile;
import material.dialog.MaterialDialog;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class PicturePresent extends BasePresent<PictureActivity> {
	public PicturePresent(Activity activity, PictureActivity view) {
		super(activity, view);
	}

	private String mPhotoPath;
	private String mUrl;

	private File mTemp;

	private Bitmap mBitmap;

	private void openCamera() {
		mPhotoPath = SDCard.osCamera +
				File.separator +
				OtherUtils.getCurrentTime() +
				Config.SUFFIX_PNG;
		ImageUtils.openCamera(mActivity, mPhotoPath);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fabCreate:
				if (mPhotoPath == null) {
					mView.showToast("您还没有选择图片", Toast.LENGTH_SHORT);
					return;
				}
				mView.showWaitDialog();
				mTemp = new File(SDCard.rootDir + File.separator + "mTemp" + Config.SUFFIX_PNG);
				try {
					FileOutputStream fos = new FileOutputStream(mTemp);
					mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
					mView.showToast("上传过程出错，请重试", Toast.LENGTH_SHORT);
					mView.dismissWaitDialog();
					return;
				}
				BmobControl.uploadFile(mActivity, mTemp, new UploadCallback());
				break;

			case R.id.imgPreview:
				final MaterialDialog dialog = new MaterialDialog(mActivity);
				dialog.setTitle("一个提示");
				dialog.setMessage("请选择生成的图片");
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
					break;
				case Config.REQ_OPEN_CAMERA:

					break;
			}
			mBitmap = ImageUtils.matrixXYBitmap(mPhotoPath, 1000, 1000);
			mView.showBitmap(mBitmap);
		} else {
			recycle();
		}
	}

	public class SaveCallback implements BmobControl.BmobSaveCallback {

		@Override
		public void onSuccess() {
			mView.dismissWaitDialog();
			mView.reset();
			mView.create(mUrl);
			recycle();
		}

		@Override
		public void onFail(String error) {
			mView.showToast(error, Toast.LENGTH_SHORT);
			mView.dismissWaitDialog();
		}
	}


	public class UploadCallback implements BmobControl.BmobUploadCallback {

		@Override
		public void onSuccess(String url, BmobFile img) {
			mUrl = url;
			if (url.startsWith(Config.KEY_SCAN_PICTURE)) {
				url = url.replace(Config.KEY_SCAN_PICTURE, Config.EMPTY_STR);
			}
			String content = mView.getContent();
			if (TextUtils.isEmpty(content)) {
				content = "这个家伙很懒，什么也没留下";
			}
			BitmapBmob bitmapBmob = new BitmapBmob(content, img, url);
			BmobControl.insertObject(mActivity, new SaveCallback(), bitmapBmob);
		}

		@Override
		public void onFail(String error) {
			mView.showToast(error, Toast.LENGTH_SHORT);
			mView.dismissWaitDialog();
		}
	}


	@Override
	public void onDestory() {
		super.onDestory();
		recycle();
	}

	private void recycle() {
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
		}
		mPhotoPath = null;
		mUrl = null;
		if (mTemp != null && mTemp.exists()) {
			mTemp.delete();
			mTemp = null;
		}
	}
}
