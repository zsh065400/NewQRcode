package org.laosao.two.present.scan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
import org.laosao.two.view.scan.ScanPictureActivity;

import java.io.File;

import cn.bmob.v3.BmobObject;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanPicturePresent extends BasePresent<ScanPictureActivity> {
	public ScanPicturePresent(Activity activity, ScanPictureActivity view) {
		super(activity, view);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mOriginUrl = mView.getContent();
		mUrl = mOriginUrl.replace(Config.KEY_SCAN_PICTURE, Config.EMPTY_STR);
		mView.showWaitDialog();
		query();
	}

	private BitmapBmob mObj;
	private File mTemp;
	private String mUrl;
	private String mOriginUrl;

	private Bitmap mBitmap;

	private void query() {
		BmobControl.queryPic(mActivity, mUrl, new BmobControl.BmobQueryCallback() {
			@Override
			public void queryZero() {
				mView.dismissWaitDialog();
				mView.showToast(R.string.query_empty, Toast.LENGTH_SHORT);
				mActivity.finish();
			}

			@Override
			public void onSuccess(BmobObject object) {
				mObj = (BitmapBmob) object;
				OtherUtils.getHttpBitmap(mOriginUrl, handler);
			}

			@Override
			public void onFail(String error) {
				mView.dismissWaitDialog();
				mView.showToast(error, Toast.LENGTH_SHORT);
				mActivity.finish();
			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case Config.CODE_ERROR:
					mView.dismissWaitDialog();
					mView.showToast(R.string.pic_load_fail, Toast.LENGTH_SHORT);
					mActivity.finish();
					break;
				case Config.CODE_YES:
					Bitmap bitmap = (Bitmap) msg.obj;
					mBitmap = ImageUtils.getImageThumbnail(bitmap, Config.BITMAP_THUMBNAIL_WIDTH
							, Config.BITMAP_THUMBNAIL_HEIGHT);
					mView.dismissWaitDialog();
					mView.setSendWord(mObj.getName());
					mView.showBitmap(mBitmap);
					break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imgPreview:
				mView.showLargePicture(mBitmap);
				break;
			case R.id.fabSave:
				OtherUtils.save(mActivity, mBitmap);
				break;

			case R.id.fabShare:
				mTemp = new File(SDCard.rootDir + File.separator + "temp" + Config.SUFFIX_PNG);
				OtherUtils.share(mActivity, mTemp, mBitmap);
				break;

			case R.id.etConent:
				String sendWord = mView.getSendWord();
				if (sendWord == null) {
					mView.showSendWordDialog("这个家伙很懒，什么也没留下");
				} else {
					mView.showSendWordDialog(sendWord);
				}
				break;
		}
	}


	@Override
	public void onDestory() {
		super.onDestory();
		recycle();
	}

	private void recycle() {
		if (mTemp != null && mTemp.exists()) {
			mTemp.delete();
		}
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
		}
		mTemp = null;
		mUrl = null;
		mObj = null;
		mOriginUrl = null;
	}
}
