package org.laosao.two.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.utils.ImageUtil;

import java.io.File;

public class AudioActivity extends BaseActivity {

	public static final int REQUEST_FILE_CODE = 0x1113;
	public static final int REQUEST_RECORDER_CODE = 0x1114;

	private File mAudioFile;
	private ButtonRectangle mBtnChooseAudio;
	private ButtonRectangle mBtnSpeak;
	private ButtonFloat mBtnCreate;
	private TextView mTvFileName;

	private String fileName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initViews();
	}

	private void initViews() {

//		mBtnChooseAudio = (ButtonRectangle) findViewById(R.id.btnChooseAudio);
//		mTvFileName = (TextView) findViewById(R.id.tvFileName);
//		mBtnCreate = (ButtonFloat) findViewById(R.id.btnCreate);
//		mBtnSpeak = (ButtonRectangle) findViewById(R.id.btnSpeak);
//
//		mBtnChooseAudio.setOnClickListener(this);
//		mBtnCreate.setOnClickListener(this);
//		mBtnSpeak.setOnClickListener(this);

	}

	private MaterialDialog pd = null;

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.btnChooseAudio:
//				Intent audio = new Intent(Intent.ACTION_PICK);
//				audio.setType(Config.IMME_AUDIO);
//				startActivityForResult(audio, REQUEST_FILE_CODE);
//				break;
//
//			case R.id.btnSpeak:
////				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//				Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
////				intent.setType(Config.IMME_RECORDER);
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, Config.rootDir + File.separator +
//						"record.mp3");
//				startActivityForResult(intent, REQUEST_RECORDER_CODE);
//				break;
//
//			case R.id.btnCreate:
//				if (mAudioFile != null && mAudioFile.exists()) {
//					pd = new MaterialDialog.Builder(this)
//							.title(R.string.upload)
//							.content(R.string.please_wait)
//							.progress(true, 0).show();
//					pd.setCanceledOnTouchOutside(false);
//
//					BmobControl.uploadImage(AudioActivity.this, new BmobControl.BmobUploadCallback() {
//						@Override
//						public void onSuccess(final String url, BmobFile audio) {
//							Audio b = new Audio(fileName, audio, url);
//							BmobControl.insertObject(AudioActivity.this, new BmobControl.BmobSaveCallback() {
//								@Override
//								public void onSuccess() {
//									startCreateActivity(Config.KEY_SCAN_PICTURE + url);
//
//									mAudioFile = null;
//									mTvFileName.setText(getString(R.string.choose_file));
//
//									pd.dismiss();
//								}
//
//								@Override
//								public void onFail(String error) {
//									Log.e("存入数据表失败", error);
//									pd.dismiss();
//								}
//							}, b);
//						}
//
//						@Override
//						public void onFail(String error) {
//							T.showShortToast(AudioActivity.this, error);
//							Log.e("数据上传失败", error);
//							pd.dismiss();
//						}
//					}, mAudioFile);
//
//				} else {
//					T.showShortToast(AudioActivity.this, "您还没有选择文件");
//				}
//				break;
//
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			String path = null;
			Uri uri = null;
			switch (requestCode) {
				case REQUEST_FILE_CODE:
					uri = data.getData();
					break;
				case REQUEST_RECORDER_CODE:
					uri = data.getData();
					break;
			}

			path = ImageUtil.getPath(AudioActivity.this, uri);
			mAudioFile = new File(path);
			final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			View view = LayoutInflater.from(this).
					inflate(R.layout.dialog_save, null);
			final MaterialEditText etName = (MaterialEditText) view.findViewById(R.id.etSave);
			AlertDialogWrapper.Builder build = new AlertDialogWrapper.Builder(this);
//			build.setTitle(getString(R.string.title_file_name));
			build.setView(view);
			build.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fileName = etName.getText().toString();
					if (TextUtils.isEmpty(fileName)) {
						fileName = mAudioFile.getName();
					}
					imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
					mTvFileName.setText(getString(R.string.current_file) + fileName);
				}
			});
			build.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fileName = mAudioFile.getName();
					imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
					mTvFileName.setText(getString(R.string.current_file) + fileName);
				}
			});
			build.show();
		}
	}
}
