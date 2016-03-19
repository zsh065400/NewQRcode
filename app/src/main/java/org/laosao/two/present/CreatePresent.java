package org.laosao.two.present;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.zbar.lib.encode.Encoder;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.CreateActivity;

import java.io.File;

import material.dialog.MaterialDialog;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class CreatePresent extends BasePresent<CreateActivity> {

	private int mQrcodeSize = 430;

	public CreatePresent(Activity activity, CreateActivity view) {
		super(activity, view);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mBitmap = encode(mView.getContent());
		mView.showBitmap(mBitmap);
	}

	private Bitmap encode(String content) {
		Encoder encoder = new Encoder.Builder()
				//字符集，默认为“utf-8”
				.setCharset("utf-8")
				//图片内边距
				.setBitmapPadding(2)
				//设置生成的图片高度
				.setBitmapHeight(mQrcodeSize)
				//设置生成的图片宽度
				.setBitmapWidth(mQrcodeSize)
				//设置二维码背景颜色，默认白色
				.setBackgroundColor(Color.WHITE)
				//设置二维码色块颜色，默认黑色
				.setCodeColor(Color.BLACK)
				.build();
		return encoder.encode(content);
	}

	private Bitmap mBitmap;
	private String mPhotoPath = null;
	private String mTempPath = null;
	private File mTemp;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fabSave:
				OtherUtils.save(mActivity, mBitmap);
				break;
			case R.id.fabShare:
				mTemp = new File(SDCard.rootDir + File.separator + "mTemp" + Config.SUFFIX_PNG);
				OtherUtils.share(mActivity, mTemp, mBitmap);
				break;

			case R.id.fabAddIcon:
				MaterialDialog dialog = new MaterialDialog(mActivity);
				dialog.setTitle(R.string.choose_logo);
				dialog.setPositiveButton("图库", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ImageUtils.openGallery(mActivity);
					}
				});
				dialog.setNegativeButton("相机", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (SDCard.sdCardInstall) {
							mPhotoPath = SDCard.osCamera +
									File.separator +
									OtherUtils.getCurrentTime() +
									Config.SUFFIX_PNG;
							ImageUtils.openCamera(mActivity, mPhotoPath);
						} else {
							mView.showToast("未检测到SD卡，无法打开图库", Toast.LENGTH_SHORT);
							return;
						}
					}
				});
				dialog.show();
				break;
		}
	}

	private void cropBitmap() {
		mTempPath = SDCard.cameraDir + File.separator +
				"剪裁" + OtherUtils.getCurrentTime() + Config.SUFFIX_PNG;
		int size = (int) (mQrcodeSize * 0.18);
		ImageUtils.cropImage(Uri.fromFile(new File(mPhotoPath)), mActivity, mTempPath, size, size);
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
					Canvas canvas = new Canvas();
					canvas.setBitmap(mBitmap);
					//向中间插入内容
					canvas.drawBitmap(mBitmap, mBitmap.getWidth() / 2
							- mBitmap.getWidth() / 2, mBitmap.getHeight()
							/ 2 - mBitmap.getHeight() / 2, null);
					mView.showBitmap(mBitmap);
					mTempPath = null;
					break;
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mView.closeFam();
	}

	@Override
	public void onDestory() {
		super.onDestory();
		if (mTemp != null && mTemp.exists()) {
			mTemp.delete();
			mTemp = null;
		}
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
		mPhotoPath = null;
	}
}
