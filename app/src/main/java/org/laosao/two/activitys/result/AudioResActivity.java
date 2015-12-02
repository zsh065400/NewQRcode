package org.laosao.two.activitys.result;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloat;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.Audio;
import org.laosao.two.utils.L;
import org.laosao.two.utils.T;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class AudioResActivity extends BaseActivity {

	private String mAudioUrl;
	private String mAudioName;
	private ButtonFloat btnPlayOrPause;
	private TextView tvFileName;
	private MediaPlayer mMediaPlayer;
	private MaterialDialog dialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_res);
		Config.setLayoutTransparentStatus(this, R.color.material);

		dialog = new MaterialDialog.Builder(this)
				.title(R.string.do_load)
				.content(R.string.please_wait)
				.progress(true, 0).show();
		dialog.setCanceledOnTouchOutside(false);

		mAudioUrl = getIntent().getStringExtra(Config.KEY_RESULT);
		init();
	}

	private void init() {
		btnPlayOrPause = (ButtonFloat) findViewById(R.id.btnPlayOrPause);
		btnPlayOrPause.setDrawableIcon(getResources().getDrawable(R.mipmap.ic_play));
		tvFileName = (TextView) findViewById(R.id.tvFileName);
		mMediaPlayer = new MediaPlayer();

		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				btnPlayOrPause.setDrawableIcon(getResources().getDrawable(R.mipmap.ic_repeat));
			}
		});

		Uri uri = Uri.parse(mAudioUrl);
		try {
			mMediaPlayer.setDataSource(this, uri);
			mMediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btnPlayOrPause.setOnClickListener(this);
		getAudioInfo(mAudioUrl);
	}

	private void getAudioInfo(String urlStr) {
		String url = urlStr.replace(Config.KEY_SCAN_PICTURE, "");
		BmobQuery<Audio> query = new BmobQuery<>();
		query.addWhereEqualTo(Config.URL_COLUMN, url);
		query.findObjects(this, new FindListener<Audio>() {
			@Override
			public void onSuccess(List<Audio> list) {
				dialog.dismiss();
				if (list != null && list.size() == 0) {
					T.showShortToast(AudioResActivity.this, "未查询到音频信息");
					finish();
					return;
				}

				mAudioName = list.get(0).getName();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tvFileName.setText(tvFileName.getText().toString() + mAudioName);
					}
				});
			}

			@Override
			public void onError(int i, String s) {
				dialog.dismiss();
				T.showShortToast(AudioResActivity.this, "获取音频信息失败，请重试");
				L.outputDebug(s);
				finish();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
			}
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				btnPlayOrPause.setDrawableIcon(getResources().getDrawable(R.mipmap.ic_play));
			} else {
				mMediaPlayer.start();
				btnPlayOrPause.setDrawableIcon(getResources().getDrawable(R.mipmap.ic_pause));
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
				mMediaPlayer.release();
			}
		}
	}
}