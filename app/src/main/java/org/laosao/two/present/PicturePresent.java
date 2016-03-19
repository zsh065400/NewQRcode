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
					mView.showToast(R.string.set_img, Toast.LENGTH_SHORT);
					return;
				}
				mView.showWaitDialog();
				BmobControl.newUploadImage(mActivity, mPhotoPath, new UploadCallback());
				break;

			case R.id.imgPreview:
				MaterialDialog dialog = new MaterialDialog(mActivity);
				dialog.setTitle("图片来源");
				dialog.setPositiveButton("图库", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ImageUtils.openGallery(mActivity);
					}
				});
				dialog.setNegativeButton("相机", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						openCamera();
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
			mBitmap = ImageUtils.getImageThumbnail(mPhotoPath,
					Config.BITMAP_THUMBNAIL_WIDTH,
					Config.BITMAP_THUMBNAIL_HEIGHT);
			mView.showBitmap(mBitmap);
		}
	}

	public class SaveCallback implements BmobControl.BmobSaveCallback {

		@Override
		public void onSuccess() {
			mView.dissmissWaitDialog();
			mView.reset();
			mView.create(mUrl);
			recycle();
		}

		@Override
		public void onFail(String error) {
			mView.showToast(error, Toast.LENGTH_SHORT);
			mView.dissmissWaitDialog();
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
			mView.dissmissWaitDialog();
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
	}
}
