package org.laosao.two.view.scan;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.model.Config;
import org.laosao.two.R;
import org.laosao.two.bean.BitmapBmob;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.view.base.BaseActivity;

import java.io.File;

import cn.bmob.v3.BmobObject;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class PicResultActivity extends BaseActivity implements MediaScannerConnection.MediaScannerConnectionClient,
		View.OnClickListener {
	private ImageView imgResult;
	private MaterialEditText etConent;
//	private ButtonFloat btnSave;
//	private ButtonFloat btnShare;
//	private FloatingActionsMenu btnMore;
	private BitmapBmob obj;
	private File temp;
	private File save;
	private String url;
	private String result;
	private String mTalk;
	private MaterialDialog dialog;
	private Bitmap bmpResult;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case Config.CODE_ERROR:
					dialog.dismiss();
					T.showLongToast(PicResultActivity.this, getString(R.string.pic_load_fail));
					finish();
					break;
				case Config.CODE_YES:
					new AsyncTask<Bitmap, Void, Bitmap>() {
						@Override
						protected Bitmap doInBackground(Bitmap... params) {
							try {
								bmpResult = params[0];
								float width = bmpResult.getWidth();
								float height = bmpResult.getHeight();
								// 创建操作图片用的matrix对象
								Matrix matrix = new Matrix();
								// 计算宽高缩放率
								float scaleWidth = ((float) Config.BITMAP_THUMBNAIL_WIDTH) / width;
								float scaleHeight = ((float) Config.BITMAP_THUMBNAIL_HEIGHT) / height;
								// 缩放图片动作
								matrix.postScale(scaleWidth, scaleHeight);
								Bitmap bmpTemp = Bitmap.createBitmap(bmpResult, 0, 0, (int) width,
										(int) height, matrix, true);
								return bmpTemp;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}

						@Override
						protected void onPostExecute(Bitmap bitmap) {
							dialog.dismiss();
							if (bitmap != null) {
								L.outputDebug("bitmap create success");
								imgResult.setImageBitmap(bitmap);
								mTalk = obj.getName();
								etConent.setText(mTalk);
							} else {
								L.outputDebug("bitmap create fail");
								finish();
							}
							super.onPostExecute(bitmap);
						}

					}.execute((Bitmap) msg.obj, null);
					break;
				case Config.CODE_PATH:
					save = (File) msg.obj;
					refreshSDcard();
					break;
			}
			try {
				finalize();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_res_image);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
		result = getIntent().getStringExtra(Config.KEY_RESULT);
		url = result.replace(Config.KEY_SCAN_PICTURE, Config.EMPTY_STR);
		dialog = new MaterialDialog.Builder(this)
				.title(R.string.do_load)
				.content(R.string.please_wait)
				.progress(true, 0).show();
		dialog.setCanceledOnTouchOutside(false);
		BmobControl.queryPic(this, url, new BmobControl.BmobQueryCallback() {
			@Override
			public void queryZero() {
				dialog.dismiss();
				T.showLongToast(PicResultActivity.this, getString(R.string.query_empty));
				PicResultActivity.this.finish();
			}

			@Override
			public void onSuccess(BmobObject object) {
				obj = (BitmapBmob) object;
				OtherUtils.getHttpBitmap(result, handler);
			}

			@Override
			public void onFail(String error) {
				dialog.dismiss();
				T.showLongToast(PicResultActivity.this, error);
				PicResultActivity.this.finish();
			}
		});
	}

	private void initView() {
//		imgResult = (ImageView) findViewById(R.id.imgResult);
//		etConent = (MaterialEditText) findViewById(R.id.etConent);
//		btnSave = (ButtonFloat) findViewById(R.id.btnSave);
//		btnShare = (ButtonFloat) findViewById(R.id.btnShare);
//		btnMore = (FloatingActionsMenu) findViewById(R.id.fam_res_img);
//		btnSave.setOnClickListener(this);
//		btnShare.setOnClickListener(this);
//		etConent.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.btnSave:
//				OtherUtils.save(this, bmpResult, handler);
//				break;
//
//			case R.id.btnShare:
//				temp = new File(Config.rootDir + File.separator + "temp" + Config.SUFFIX_PNG);
//				OtherUtils.share(this, temp, bmpResult);
//				break;
//
//			case R.id.etConent:
//				MaterialDialog.Builder builder = new MaterialDialog.Builder(PicResultActivity.this);
//				builder.content(mTalk);
//				builder.build().show();
//				break;
//		}
	}

	@Override
	protected void onStop() {
		super.onStop();
//		if (btnMore.isExpanded()) {
//			btnMore.collapse();
//		}
	}

	@Override
	protected void onDestroy() {
		if (temp != null && temp.exists()) {
			temp.delete();
		}
		if (bmpResult != null && !bmpResult.isRecycled()) {
			L.outputDebug(L.CAN_RECYCLE_BITMAP);
			L.outputDebug(L.RECYCLE_BITMAP);
			bmpResult.recycle();
			bmpResult = null;
		}
		temp = null;
		save = null;
		url = null;
		result = null;
		dialog = null;
		super.onDestroy();
	}


	private MediaScannerConnection msc;

	@Override
	public void onMediaScannerConnected() {
		msc.scanFile(save.getAbsolutePath(), Config.IMME_IMAGE_TYPE);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		msc.disconnect();
		L.outputInfo("刷新路径：" + path);
		L.outputInfo("刷新Uri：" + uri.toString());
	}

	/**
	 * 开始刷新
	 */
	private void refreshSDcard() {
		if (msc != null) {
			msc.disconnect();
		}
		msc = new MediaScannerConnection(this, this);
		msc.connect();
	}
}
