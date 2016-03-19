package org.laosao.two.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.AudioPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IAudioView;

import java.io.File;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

public class AudioActivity extends BaseActivity<AudioPresent> implements IAudioView {

	private ButtonRectangle mBtnChoose;
	private ButtonRectangle mBtnRecord;
	private FloatingActionButton mFabCreate;
	private TextView mTvFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		mPresent = new AudioPresent(this, this);
	}

	@Override
	public void initView() {
		mBtnChoose = (ButtonRectangle) findViewById(R.id.btnChooseAudio);
		mBtnRecord = (ButtonRectangle) findViewById(R.id.btnSpeak);
		mTvFileName = (TextView) findViewById(R.id.tvFileName);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mBtnChoose.setOnClickListener(this);
		mBtnRecord.setOnClickListener(this);
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
	}

	private MaterialDialog mDialog = null;

	@Override
	public void pickAudioForResult() {
		Intent audio = new Intent(Intent.ACTION_PICK);
		audio.setType(Config.IMME_AUDIO);
		startActivityForResult(audio, Config.REQ_FILE_CODE);
	}

	@Override
	public void startRecord() {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
		intent.setType(Config.IMME_RECORDER);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, SDCard.rootDir + File.separator +
				"record.mp3");
		startActivityForResult(intent, Config.REQ_RECORDER_CODE);
	}

	@Override
	public void showUploadDialog() {
		mDialog = new MaterialDialog(this)
				.setTitle(R.string.upload)
				.setView(getLayoutInflater().inflate(R.layout.dialog_progress, null));
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();
	}

	@Override
	public void showSaveDialog(final File file) {
		final InputMethodManager imm = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = LayoutInflater.from(this).
				inflate(R.layout.dialog_save, null);
		final MaterialEditText etName = (MaterialEditText) view.findViewById(R.id.etSave);
		MaterialDialog dialog = new MaterialDialog(this);
		dialog.setTitle("自定义名称");
		dialog.setView(view);
		dialog.setPositiveButton(R.string.ok, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fileName = etName.getText().toString();
				if (TextUtils.isEmpty(fileName)) {
					fileName = file.getName();
				}
				imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
				setFileName(getString(R.string.current_file) + fileName);
			}
		});
		dialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fileName = file.getName();
				imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
				setFileName(getString(R.string.current_file) + fileName);
			}
		});
		dialog.show();
	}

	@Override
	public void dissmissDialog() {
		if (mDialog != null)
			mDialog.dismiss();
	}

	@Override
	public void setFileName(String fileName) {
		mTvFileName.setText(fileName);
	}

	@Override
	public void setFileName(int resId) {
		String s = getString(resId);
		setFileName(s);
	}

}
