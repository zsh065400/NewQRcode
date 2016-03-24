package org.laosao.two.present;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.UserFile;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.Config;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.FileActivity;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class FilePresent extends BasePresent<FileActivity> {

	public FilePresent(Activity activity, FileActivity view) {
		super(activity, view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.btnChooseFile:
				mView.getFile();
				break;

			case R.id.fabCreate:
				uploadFile();
				break;
		}
	}

	private File mFile;
	private String mFileName;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == mActivity.RESULT_OK) {
			String path = null;
			Uri uri = null;
			switch (requestCode) {
				case Config.REQ_FILE_CODE:
					uri = data.getData();
					break;
			}
			path = ImageUtils.getPath(mActivity, uri);
			mFile = new File(path);
			mFileName = mFile.getName();
			mView.setFileName(mFileName);
		}
	}


	private void uploadFile() {
		if (mFile != null && mFile.exists()) {
			mView.showUploadDialog();
			BmobControl.newUploadImage(mActivity, mFile.getAbsolutePath(), new BmobControl.BmobUploadCallback() {
				@Override
				public void onSuccess(final String url, BmobFile file) {
					final String u = url.replace("http://", "");
					UserFile b = new UserFile(mFileName, u, file);
					BmobControl.insertObject(mActivity, new BmobControl.BmobSaveCallback() {
						@Override
						public void onSuccess() {
							mView.dissmissDialog();
							mView.create(u);
							mView.setFileName(" ");
							mFile = null;
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
