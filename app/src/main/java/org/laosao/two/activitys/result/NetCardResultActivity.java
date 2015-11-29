package org.laosao.two.activitys.result;

import android.os.Bundle;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.laosao.two.Config;
import org.laosao.two.R;
import org.laosao.two.activitys.base.BaseActivity;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.biz.BmobControl;
import org.laosao.two.utils.T;

import cn.bmob.v3.BmobObject;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class NetCardResultActivity extends BaseActivity {
	private ImageView imgPreview;
	private MaterialEditText etName;
	private MaterialEditText etQq;
	private MaterialEditText etWechat;
	private MaterialEditText etAddress;
	private MaterialEditText etCompany;
	private MaterialEditText etJob;
	private MaterialEditText etEmail;
	private MaterialEditText etPhone;
	private MaterialEditText etWeibo;
	private MaterialEditText etFax;
	private MaterialEditText etProFile;
	private String url;
	private PersonIdCard obj;
	private MaterialDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_net_card);
		Config.setLayoutTransparentStatus(this, R.color.material);
		initView();
		url = getIntent().getStringExtra(Config.KEY_RESULT);
		dialog = new MaterialDialog.Builder(this)
				.title(R.string.do_load)
				.content(R.string.please_wait)
				.progress(true, 0).show();
		dialog.setCanceledOnTouchOutside(false);
		BmobControl.queryNetCard(this, url, new BmobControl.BmobQueryCallback() {
			@Override
			public void queryZero() {
				dialog.dismiss();
				T.showLongToast(NetCardResultActivity.this, getString(R.string.query_empty));
				NetCardResultActivity.this.finish();
			}

			@Override
			public void onSuccess(BmobObject object) {
				dialog.dismiss();
				obj = (PersonIdCard) object;
				setUIContent();
			}

			@Override
			public void onFail(String error) {
				dialog.dismiss();
				T.showLongToast(NetCardResultActivity.this, error);
				NetCardResultActivity.this.finish();
			}
		});
	}


	private void initView() {
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		etName = (MaterialEditText) findViewById(R.id.etName);
		etQq = (MaterialEditText) findViewById(R.id.etQq);
		etWechat = (MaterialEditText) findViewById(R.id.etWechat);
		etAddress = (MaterialEditText) findViewById(R.id.etAddress);
		etCompany = (MaterialEditText) findViewById(R.id.etCompany);
		etJob = (MaterialEditText) findViewById(R.id.etJob);
		etEmail = (MaterialEditText) findViewById(R.id.etEmail);
		etPhone = (MaterialEditText) findViewById(R.id.etPhone);
		etWeibo = (MaterialEditText) findViewById(R.id.etWeibo);
		etFax = (MaterialEditText) findViewById(R.id.etFax);
		etProFile = (MaterialEditText) findViewById(R.id.etProFile);
	}

	private void setUIContent() {
		obj.getHead().loadImage(this, imgPreview);
		etName.setText(obj.getName());
		etQq.setText(obj.getQq());
		etWechat.setText(obj.getWechat());
		etAddress.setText(obj.getAddress());
		etCompany.setText(obj.getCompany());
		etJob.setText(obj.getJob());
		etEmail.setText(obj.getE_email());
		etPhone.setText(obj.getPhone());
		etWeibo.setText(obj.getWeibo());
		etFax.setText(obj.getChuanzhen());
		etProFile.setText(obj.getPerson());
		try {
			finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

}
