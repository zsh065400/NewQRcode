package org.laosao.two.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.CreatePresent;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.ICreateView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;
import material.view.fab.FloatingActionsMenu;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class CreateActivity extends BaseActivity<CreatePresent>
        implements ICreateView {

    private ImageView mShow;
    private FloatingActionButton mFabShare;
    private FloatingActionButton mFabSave;
    private FloatingActionButton mFabAddLogo;
    private FloatingActionsMenu mFamMore;

    private String mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mContent = getIntent().getStringExtra(Config.KEY_CONTENT);
    }

    @Override
    public BasePresent createPersent() {
        return new CreatePresent(this, this);
    }

    private MaterialDialog mDialog = null;

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
    public void initView() {
        mShow = (ImageView) findViewById(R.id.create_show);
        mFabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        mFabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        mFamMore = (FloatingActionsMenu) findViewById(R.id.famMore);
        mFabAddLogo = (FloatingActionButton) findViewById(R.id.fabAddIcon);
    }

    @Override
    public void setListener() {
        mFabShare.setOnClickListener(this);
        mFabSave.setOnClickListener(this);
        mFabAddLogo.setOnClickListener(this);
    }

    @Override
    public void showBitmap(Bitmap bitmap) {
        mShow.setImageBitmap(bitmap);
    }

    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void closeFam() {
        if (mFamMore.isExpanded())
            mFamMore.collapse();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeFam();
    }

}
