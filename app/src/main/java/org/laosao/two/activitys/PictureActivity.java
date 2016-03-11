package org.laosao.two.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.BitmapBmob;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.ImageUtil;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class PictureActivity extends BaseActivity implements BmobControl.BmobUploadCallback,
		BmobControl.BmobSaveCallback {


	private ImageView imgPreview;
	private MaterialEditText etConent;
	private ButtonFloat btnCreate;

	private String[] menuItem = {"相机", "图库"};
	private String photoPath;
	private String url;

	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		Config.setLayoutTransparentStatus(this, R.color.material);
		init();
	}


	private void init() {
//		imgPreview = (ImageView) findViewById(R.id.imgPreview);
//		etConent = (MaterialEditText) findViewById(R.id.etConent);
//		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
//		btnCreate.setOnClickListener(this);
//		imgPreview.setOnClickListener(this);

//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//
//                //比较Activity根布局与当前布局的大小
//                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
//                if (heightDiff > 100) {
//                    //大小超过100时，一般为显示虚拟键盘事件
//                    btnCreate.setVisibility(View.GONE);
//                } else {
//                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
//                    btnCreate.setVisibility(View.VISIBLE);
//                }
//            }
//        });
	}

	private MaterialDialog pd = null;


	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.btnCreate:
//				if (photoPath == null) {
//					T.showLongToast(PictureActivity.this, getString(R.string.set_img));
//					return;
//				}
//				pd = new MaterialDialog.Builder(this)
//						.title(R.string.upload)
//						.content(R.string.please_wait)
//						.progress(true, 0).show();
//				pd.setCanceledOnTouchOutside(false);
//
//				//				File img = new File(photoPath);
//				//				BmobControl.uploadImage(this, this, img);
//				// TODO: 2015/8/30 新版上传
//				BmobControl.newUploadImage(this, photoPath, this);
//				break;
//
//			case R.id.imgPreview:
//				AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(this);
//				builder.setTitle(R.string.title_please_choose);
//				builder.setItems(menuItem, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						switch (which) {
//							case 0:
//								if (Config.sdCardInstall) {
//									openAndSet();
//								} else {
//									T.showLongToast(PictureActivity.this, getString(R.string.fail_to_mounted_sdcard));
//									return;
//								}
//								break;
//							case 1:
//								ImageUtil.openImg(PictureActivity.this);
//								break;
//						}
//					}
//				});
//				builder.create().show();
//				break;
//
//		}
	}

	private void openAndSet() {
		photoPath = Config.osCamera +
				File.separator +
				GeneralUtil.getTime() +
				Config.SUFFIX_PNG;
		ImageUtil.openCamera(this, photoPath);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case Config.REQ_OPEN_IMG:
					photoPath = ImageUtil.getPath(this, data.getData());
					break;
				case Config.REQ_OPEN_CAMERA:

					break;
			}
			bitmap = ImageUtil.getImageThumbnail(photoPath,
					Config.BITMAP_THUMBNAIL_WIDTH,
					Config.BITMAP_THUMBNAIL_HEIGHT);
			imgPreview.setImageBitmap(bitmap);
		}
	}

	@Override
	public void onSuccess() {
		pd.dismiss();
		// TODO: 2016/3/2
//		imgPreview.setImageResource(R.drawable.bac);
		etConent.setText(Config.EMPTY_STR);
		startCreateActivity(url);

		bitmap = null;
		photoPath = null;
		url = null;
		try {
			L.outputDebug("准备执行清理");
			finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			L.outputDebug(L.CAN_RECYCLE_BITMAP);
			L.outputDebug(L.RECYCLE_BITMAP);
			bitmap.recycle();
			bitmap = null;
		}
		super.onDestroy();
	}

	//上传回调接口
	@Override
	public void onSuccess(String url, BmobFile img) {
		this.url = url;
		if (url.startsWith(Config.KEY_SCAN_PICTURE)) {
			url = url.replace(Config.KEY_SCAN_PICTURE, Config.EMPTY_STR);
		}
		String content = etConent.getText().toString();
		if (TextUtils.isEmpty(content)) {
			content = "图片的主人什么也没有写";
		}
		BitmapBmob bitmapBmob = new BitmapBmob(content, img, url);
		BmobControl.insertObject(this, this, bitmapBmob);
	}

	@Override
	public void onFail(String error) {
		pd.dismiss();
		T.showLongToast(this, error);
	}
}
