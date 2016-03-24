package org.laosao.two.present;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.AudioActivity;
import org.laosao.two.bean.Audio;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.Config;
import org.laosao.two.model.ImageUtils;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class AudioPresent extends BasePresent<AudioActivity> {

	private File mAudioFile;
	private String mFileName = null;

	public AudioPresent(Activity activity, AudioActivity view) {
		super(activity, view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnChooseAudio:
				mView.pickAudioForResult();
				break;

			case R.id.btnSpeak:
				mView.startRecord();
				break;

			case R.id.fabCreate:
				uploadFile();
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == mActivity.RESULT_OK) {
			String path = null;
			Uri uri = null;
			switch (requestCode) {
				case Config.REQ_FILE_CODE:
					uri = data.getData();
					break;
				case Config.REQ_RECORDER_CODE:
					uri = data.getData();
					break;
			}

			path = ImageUtils.getPath(mActivity, uri);
			mAudioFile = new File(path);
			mFileName = mAudioFile.getName();
			mView.showSaveDialog(mAudioFile);
		}
	}

	private void uploadFile() {
		if (mAudioFile != null && mAudioFile.exists()) {
			mView.showUploadDialog();
			BmobControl.newUploadImage(mActivity, mAudioFile.getAbsolutePath(),
					new BmobControl.BmobUploadCallback() {
						@Override
						public void onSuccess(final String url, BmobFile audio) {
							final String u = url.replace(Config.KEY_SCAN_PICTURE, "");
							Audio b = new Audio(mFileName, audio, u);
							BmobControl.insertObject(mActivity, new BmobControl.BmobSaveCallback() {
								@Override
								public void onSuccess() {
									mView.dissmissDialog();
									mView.create(Config.KEY_SCAN_PICTURE + u);
									mView.setFileName(mActivity.getString(R.string.choose_file));
									mAudioFile = null;
								}

								@Override
								public void onFail(String error) {
									Log.e("存入数据表失败", error);
									mView.dissmissDialog();
								}
							}, b);
						}

						@Override
						public void onFail(String error) {
							mView.showToast(error, Toast.LENGTH_LONG);
							Log.e("数据上传失败", error);
							mView.dissmissDialog();
						}
					});
		} else {
			mView.showToast("您还没有选择文件", Toast.LENGTH_LONG);
		}
	}
}
