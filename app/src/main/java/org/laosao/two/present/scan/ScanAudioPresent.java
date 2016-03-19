package org.laosao.two.present.scan;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.bean.Audio;
import org.laosao.two.model.Config;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.scan.ScanAudioActivity;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanAudioPresent extends BasePresent<ScanAudioActivity> {

	private MediaPlayer mMediaPlayer;

	private String mAudioUrl;

	public ScanAudioPresent(Activity activity, ScanAudioActivity view) {
		super(activity, view);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mAudioUrl = mView.getContent();
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mView.changState(2);
			}
		});
		Uri uri = Uri.parse(mAudioUrl);
		try {
			mMediaPlayer.setDataSource(mActivity, uri);
			mMediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initAudioInfo();
	}

	private void initAudioInfo() {
		String url = mAudioUrl.replace(Config.KEY_SCAN_PICTURE, "");
		BmobQuery<Audio> query = new BmobQuery<>();
		query.addWhereEqualTo(Config.URL_COLUMN, url);
		query.findObjects(mActivity, new FindListener<Audio>() {
			@Override
			public void onSuccess(List<Audio> list) {
				mView.dismissWaitDialog();
				if (list != null && list.size() == 0) {
					mView.showToast("声音神秘的消失了", Toast.LENGTH_SHORT);
					mActivity.finish();
				} else {
					final String mAudioName = list.get(0).getName();
					mActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mView.setAudioName(mAudioName);
							mMediaPlayer.start();
							mView.changState(1);
						}
					});
				}
			}

			@Override
			public void onError(int i, String s) {
				mView.dismissWaitDialog();
				mView.showToast("声音神秘的消失了", Toast.LENGTH_SHORT);
				mActivity.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mView.changState(0);
			} else {
				mMediaPlayer.start();
				mView.changState(1);
			}
		}
	}

	@Override
	public void onPause() {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mView.changState(0);
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestory() {
		super.onDestory();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
				mMediaPlayer.release();
			}
		}
	}
}
