package org.laosao.two.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.ImageUtil;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class NetCardActivity extends BaseActivity implements BmobControl.BmobSaveCallback,
		BmobControl.BmobUploadCallback {
	private ImageView imgPreview;
	private MaterialEditText etName, etQq, etWechat, etAddress, etCompany, etJob, etEmail, etPhone, etWeibo, etFax, etProFile;
	private ButtonFloat btnCreate;
	private List<MaterialEditText> lists;
	private String[] menuItem = {"相机", "图库"};
	private String photoPath = null;
	private String tempPath = null;
	private String url = null;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_card);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
	}

	private void initView() {
//		imgPreview = (ImageView) findViewById(R.id.imgPreview);
//		etName = (MaterialEditText) findViewById(R.id.etName);
//		etQq = (MaterialEditText) findViewById(R.id.etQq);
//		etWechat = (MaterialEditText) findViewById(R.id.etWechat);
//		etAddress = (MaterialEditText) findViewById(R.id.etAddress);
//		etCompany = (MaterialEditText) findViewById(R.id.etCompany);
//		etJob = (MaterialEditText) findViewById(R.id.etJob);
//		etEmail = (MaterialEditText) findViewById(R.id.etEmail);
//		etPhone = (MaterialEditText) findViewById(R.id.etPhone);
//		etWeibo = (MaterialEditText) findViewById(R.id.etWeibo);
//		etFax = (MaterialEditText) findViewById(R.id.etFax);
//		etProFile = (MaterialEditText) findViewById(R.id.etProFile);
//		btnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
//		btnCreate.setOnClickListener(this);
//		imgPreview.setOnClickListener(this);
//
//		lists = new ArrayList<>();
//		lists.add(etName);
//		lists.add(etQq);
//		lists.add(etWechat);
//		lists.add(etAddress);
//		lists.add(etCompany);
//		lists.add(etJob);
//		lists.add(etEmail);
//		lists.add(etPhone);
//		lists.add(etWeibo);
//		lists.add(etFax);
//		lists.add(etProFile);


	}

	private MaterialDialog pd = null;

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.btnCreate:
//				if (TextUtils.isEmpty(etName.getText().toString()) || tempPath == null) {
//					T.showLongToast(this, getString(R.string.head_name_not_empty));
//					return;
//				}
//				pd = new MaterialDialog.Builder(this)
//						.title(R.string.upload)
//						.content(R.string.please_wait)
//						.progress(true, 0).show();
//				pd.setCanceledOnTouchOutside(false);
//
////				File file = new File(tempPath);
////				BmobControl.uploadImage(this, this, file);
//				// TODO: 2015/8/30 新版应用
//				BmobControl.newUploadImage(this, tempPath, this);
//				break;
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
//									T.showLongToast(NetCardActivity.this, getString(R.string.fail_to_mounted_sdcard));
//									return;
//								}
//								break;
//							case 1:
//								ImageUtil.openImg(NetCardActivity.this);
//								break;
//						}
//					}
//				});
//				builder.create().show();
//				break;
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
					doCrop();
					break;
				case Config.REQ_OPEN_CAMERA:
					doCrop();
					break;
				case Config.REQ_CROP_IMG:
					bitmap = BitmapFactory.decodeFile(tempPath);
					imgPreview.setImageBitmap(bitmap);
					break;
			}

		}
	}


	private void doCrop() {
		tempPath = Config.cameraDir + File.separator + "剪裁" + GeneralUtil.getTime() + Config.SUFFIX_PNG;
		ImageUtil.cropImage(Uri.fromFile(new File(photoPath)), this, tempPath, 200, 210);
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

	@Override
	public void onSuccess() {
		// TODO: 2016/3/2 解决图片
//		imgPreview.setImageResource(R.drawable.bac);
		tempPath = null;
		for (int i = 0; i < lists.size(); i++) {
			lists.get(i).setText(Config.EMPTY_STR);
		}
		pd.dismiss();
		startCreateActivity(url);

		bitmap = null;
		photoPath = null;
		tempPath = null;
		url = null;
		try {
			finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	@Override
	public void onSuccess(String url, BmobFile img) {
		this.url = Config.KEY_SCAN_NET_CARD + url;
		PersonIdCard card = new PersonIdCard(etName.getText().toString(),
				img,
				etQq.getText().toString(),
				etWechat.getText().toString(),
				etAddress.getText().toString(),
				etCompany.getText().toString(),
				etJob.getText().toString(),
				etEmail.getText().toString(),
				etPhone.getText().toString(),
				etWeibo.getText().toString(),
				etFax.getText().toString(),
				etProFile.getText().toString(),
				url);
		BmobControl.insertObject(this, this, card);
	}

	@Override
	public void onFail(String error) {
		pd.dismiss();
		T.showLongToast(this, error);
	}
}

