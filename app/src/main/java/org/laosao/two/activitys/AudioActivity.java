package org.laosao.two.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.laosao.two.R;
import org.laosao.two.bean.BitmapBmob;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.ImageUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

public class AudioActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		// TODO: 2015/10/28 测试音频视频上传
		Intent audio = new Intent(Intent.ACTION_PICK);
		audio.setType("audio/*");
		startActivityForResult(audio, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TODO: 2015/10/28 测试位置
		Uri uri = data.getData();
		String path = ImageUtil.getPath(AudioActivity.this, uri);
		File file = new File(path);

		BmobControl.uploadImage(AudioActivity.this, new BmobControl.BmobUploadCallback() {
			@Override
			public void onSuccess(String url, BmobFile img) {
				BitmapBmob b = new BitmapBmob("test", img, "aaaaaaaa");
				BmobControl.insertObject(AudioActivity.this, null, b);
			}

			@Override
			public void onFail(String error) {

			}
		}, file);
	}
}
