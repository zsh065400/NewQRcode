package org.laosao.two.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.FilePresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IFileView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class FileActivity extends BaseActivity<FilePresent>
		implements IFileView {

	private TextView mTvFileName;
	private ButtonRectangle mBtnChooseFile;
	private FloatingActionButton mFabCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		mPresent = new FilePresent(this, this);
	}

	@Override
	public void initView() {
		mTvFileName = (TextView) findViewById(R.id.tvFileName);
		mBtnChooseFile = (ButtonRectangle) findViewById(R.id.btnChooseFile);
		mFabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
	}

	@Override
	public void setListener() {
		mBtnChooseFile.setOnClickListener(this);
		mFabCreate.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		return null;
	}


	private MaterialDialog mDialog;

	@Override
	public void showUploadDialog() {
		mDialog = OtherUtils.showWaitDialog(this);
	}

	@Override
	public void setFileName(String fileName) {
		mTvFileName.setText(mTvFileName.getText().toString()
				+ fileName);
	}

	@Override
	public void dissmissDialog() {
		if (mDialog != null)
			mDialog.dismiss();
	}

	@Override
	public void getFile() {
		Intent audio = new Intent(Intent.ACTION_PICK);
		audio.setType(Config.IMME_ALL);
		startActivityForResult(audio, Config.REQ_FILE_CODE);
	}
}
