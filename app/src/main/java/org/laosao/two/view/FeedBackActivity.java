package org.laosao.two.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.R;
import org.laosao.two.present.FeedbackPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IFeedbackView;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class FeedBackActivity extends BaseActivity<FeedbackPresent>
		implements IFeedbackView {
	private MaterialEditText mEtTheme;
	private MaterialEditText mEtContent;
	private MaterialEditText mEtContact;
	private FloatingActionButton mFabSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public FeedbackPresent createPersent() {
        return new FeedbackPresent(this, this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
	public void initView() {
		mEtTheme = (MaterialEditText) findViewById(R.id.etProject);
		mEtContent = (MaterialEditText) findViewById(R.id.etContent);
		mEtContact = (MaterialEditText) findViewById(R.id.etContact);
		mFabSend = (FloatingActionButton) findViewById(R.id.fabSend);
	}

    @Override
	public void setListener() {
		mFabSend.setOnClickListener(this);
	}

	@Override
	public String getContent() {
		String theme = mEtTheme.getText().toString();
		String content = mEtContent.getText().toString();
		if (TextUtils.isEmpty(theme) || TextUtils.isEmpty(content)) {
			return null;
		}
		String contact = mEtContact.getText().toString();
		return theme + ":" + content + ":" + contact;
	}

	@Override
	public void showDialog() {
		MaterialDialog dialog = new MaterialDialog(this);
		dialog.setTitle(R.string.fb_success);
		dialog.setMessage(R.string.fb_msg);
		dialog.setPositiveButton("加油", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		dialog.show();
	}
}
