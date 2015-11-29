package org.laosao.two.activitys;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gc.materialdesign.views.ButtonFloat;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.CreatQrcodeUtil;
import org.laosao.two.utils.GeneralUtil;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import java.io.File;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class CreateActivity extends BaseActivity implements MediaScannerConnection.MediaScannerConnectionClient {

	private static Bitmap bitmap;
	private static MaterialDialog pd = null;
	
	private static ImageView createshow;
	private ButtonFloat btnShare;
	private ButtonFloat btnSave;
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
		pd = new MaterialDialog(this)
				     .setTitle(R.string.do_create)
				     .setMessage(R.string.please_wait)
				     .setView(new ProgressBar(CreateActivity.this));

		pd.setCanceledOnTouchOutside(false);
		pd.show();
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
		btnShare.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}
	
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
		}
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
