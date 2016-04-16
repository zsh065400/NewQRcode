package org.laosao.two.view.scan;

import android.os.Bundle;
import android.widget.TextView;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.scan.ScanAudioPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.scan.IScanAudioView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

public class ScanAudioActivity extends BaseActivity<ScanAudioPresent>
        implements IScanAudioView {

    private FloatingActionButton mFabPlayOrPause;
    private TextView mTvfilename;

    private MaterialDialog mDialog;

    private String mAudioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ScanAudioPresent createPersent() {
        return new ScanAudioPresent(this, this);
    }

    @Override
    public int getContentView() {
        return R.layout.scan_activity_audio;
    }

    @Override
    public void initView() {
        mFabPlayOrPause = (FloatingActionButton) findViewById(R.id.fabPlayOrPause);
        mTvfilename = (TextView) findViewById(R.id.tvFileName);
    }

    @Override
    public void loadData() {
        mAudioUrl = getIntent().getStringExtra(Config.KEY_RESULT);
    }

    @Override
    public void setListener() {
        mFabPlayOrPause.setOnClickListener(this);
    }

    @Override
    public String getContent() {
        return mAudioUrl;
    }

    @Override
    public void changState(int state) {
//		0代表暂停,1播放，2重复
        switch (state) {
            case 0:
                mFabPlayOrPause.setIconDrawable(getResources().getDrawable(R.mipmap.ic_play));
                break;
            case 1:
                mFabPlayOrPause.setIconDrawable(getResources().getDrawable(R.mipmap.ic_pause));
                break;
            case 2:
                mFabPlayOrPause.setIconDrawable(getResources().getDrawable(R.mipmap.ic_repeat));
                break;
        }
    }

    @Override
    public void showWaitDialog() {
        mDialog = OtherUtils.showWaitDialog(this);
    }

    @Override
    public void dismissWaitDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    @Override
    public void setAudioName(String s) {
        mTvfilename.setText(mTvfilename.getText().toString() + s);
    }
}