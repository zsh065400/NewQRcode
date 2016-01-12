package org.laosao.two.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.CreatQrcodeUtil;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.ImageUtil;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import java.io.File;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class CreateActivity extends BaseActivity implements MediaScannerConnection.MediaScannerConnectionClient {

	private static Bitmap bitmap;
	private static MaterialDialog pd = null;

	private static ImageView createshow;
	private ButtonFloat btnShare;
	private ButtonFloat btnSave;
	private ButtonFloat btnAddLogo;
	private FloatingActionsMenu btnMore;
	private String content;
	private int color;
	private File temp;
	private File save;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			switch (msg.what) {
				case Config.CODE_YES:
					bitmap = (Bitmap) msg.obj;
					createshow.setImageBitmap(bitmap);
					break;
				case Config.CODE_ERROR:
					T.showLongToast(CreateActivity.this, getString(R.string.fail_to_create));
					finish();
					break;
				case Config.CODE_PATH:
					save = (File) msg.obj;
					refreshSDcard();
					break;
				case Config.CODE_ADD_LOGO:
					createshow.setImageBitmap(bitmap);
					dialog.dismiss();
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
		setContentView(R.layout.activity_create);
		Config.setLayoutTransparentStatus(this, R.color.material);
		content = getIntent().getStringExtra(Config.KEY_CONTENT);

		int lenght = content.length();
		color = getResources().getColor(R.color.black);
		L.outputInfo(content);
		L.outputInfo(lenght + "");
		initView();
		pd = new MaterialDialog.Builder(this)
				.title(R.string.do_create)
				.content(R.string.please_wait)
				.progress(true, 0).show();
		pd.setCanceledOnTouchOutside(false);
		doCreate(lenght);
	}

	private void doCreate(int lenght) {

		int level = CreatQrcodeUtil.LENGTH_LV0;

		if (lenght < 25) {

		} else if (lenght < 55) {
			level = CreatQrcodeUtil.LENGTH_LV1;
		} else if (lenght < 90) {
			level = CreatQrcodeUtil.LENGTH_LV2;
		} else if (lenght < 150) {
			level = CreatQrcodeUtil.LENGTH_LV3;
		} else if (lenght <= 200) {
			level = CreatQrcodeUtil.LENGTH_LV4;
		} else {
			T.showLongToast(this, getString(R.string.too_long));
			finish();
			return;
		}

		CreatQrcodeUtil.createQRcode(color, content, level, handler);

	}

	private void initView() {
		createshow = (ImageView) findViewById(R.id.create_show);
		btnShare = (ButtonFloat) findViewById(R.id.btnShare);
		btnSave = (ButtonFloat) findViewById(R.id.btnSave);
		btnMore = (FloatingActionsMenu) findViewById(R.id.fam_create);
		btnAddLogo = (ButtonFloat) findViewById(R.id.btnAddLogo);
		btnShare.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnAddLogo.setOnClickListener(this);
	}

	private String photoPath = null;
	private String tempPath = null;
	private String[] menuItem = {"相机", "图库"};
	private Bitmap bmpLogo = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSave:
				GeneralUtil.save(this, bitmap, handler);
				break;
			case R.id.btnShare:
				temp = new File(Config.rootDir + File.separator + "temp" + Config.SUFFIX_PNG);
				GeneralUtil.share(CreateActivity.this, temp, bitmap);
				break;

			case R.id.btnAddLogo:
				AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(this);
				builder.setTitle(R.string.title_please_choose);
				builder.setItems(menuItem, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								if (Config.sdCardInstall) {
									openAndSet();
								} else {
									T.showLongToast(CreateActivity.this, getString(R.string.fail_to_mounted_sdcard));
									return;
								}
								break;
							case 1:
								ImageUtil.openImg(CreateActivity.this);
								break;
						}
					}
				});
				builder.create().show();
				break;
		}
	}

	private void openAndSet() {
		photoPath = Config.osCamera +
				File.separator +
				GeneralUtil.getTime() +
				Config.SUFFIX_PNG;
		ImageUtil.openCamera(this, photoPath);
	}

	MaterialDialog dialog = null;

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
					bmpLogo = BitmapFactory.decodeFile(tempPath);
					if (dialog == null) {
						dialog = new MaterialDialog.Builder(CreateActivity.this).
								progress(true, 0)
								.content(R.string.please_wait).
										build();
					}
					dialog.show();
					Canvas canvas = new Canvas();
					canvas.setBitmap(bitmap);
					//向中间插入内容
					canvas.drawBitmap(bmpLogo, bitmap.getWidth() / 2
							- bmpLogo.getWidth() / 2, bitmap.getHeight()
							/ 2 - bmpLogo.getHeight() / 2, null);
					handler.sendEmptyMessage(Config.CODE_ADD_LOGO);
					break;
			}

		}
	}


	private void doCrop() {
		tempPath = Config.cameraDir + File.separator + "剪裁" + GeneralUtil.getTime() + Config.SUFFIX_PNG;
		int size = (int) (CreatQrcodeUtil.imgSize * 0.18);
		ImageUtil.cropImage(Uri.fromFile(new File(photoPath)), this, tempPath, size, size);
	}

	@Override
	protected void onDestroy() {
		if (temp != null && temp.exists()) {
			temp.delete();
		}
		bitmap = null;
		temp = null;
		save = null;
		pd = null;
		bmpLogo = null;
		tempPath = null;
		photoPath = null;
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (btnMore.isExpanded()) {
			btnMore.collapse();
		}
	}

	private MediaScannerConnection msc;

	@Override
	public void onMediaScannerConnected() {
		msc.scanFile(save.getAbsolutePath(), Config.IMME_IMAGE_TYPE);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		msc.disconnect();
		L.outputInfo(getString(R.string.refresh_path) + path);
		L.outputInfo(getString(R.string.refresh_uri) + uri.toString());
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
