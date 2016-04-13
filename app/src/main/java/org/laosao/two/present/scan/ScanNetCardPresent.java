package org.laosao.two.present.scan;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.model.BmobControl;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.scan.ScanNetCardActivity;

import cn.bmob.v3.BmobObject;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class ScanNetCardPresent extends BasePresent<ScanNetCardActivity> {
    public ScanNetCardPresent(Activity activity, ScanNetCardActivity view) {
        super(activity, view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        query();
        mView.showWaitDialog();
    }

    public void saveView() {
        Bitmap drawingCache = ImageUtils.getBitmapByView(mView.getView());
        drawingCache = ImageUtils.compressImage(drawingCache, 200);
        OtherUtils.save(mActivity, drawingCache);
    }


    @Override
    public void granted(String[] permission) {
        saveView();
    }

    @Override
    public void denied(String[] permission) {
        mView.showToast(mActivity.getString(R.string.write_permission_denied),
                Toast.LENGTH_LONG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabSave:
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;

            case R.id.etPerson:
                String s = mView.getIntroduce();
                if (TextUtils.isEmpty(s)) {
                    return;
                } else {
                    mView.showDialog(s);
                }
                break;
        }
    }

    private PersonIdCard obj;

    private void query() {
        BmobControl.queryNetCard(mActivity, mView.getContent(),
                new BmobControl.BmobQueryCallback() {
                    @Override
                    public void queryZero() {
                        mView.dismissWaitDialog();
                        mView.showToast("信息神秘的消失了", Toast.LENGTH_SHORT);
                        mActivity.finish();
                    }

                    @Override
                    public void onSuccess(BmobObject object) {
                        mView.dismissWaitDialog();
                        obj = (PersonIdCard) object;
                        mView.setContent(obj);
                    }

                    @Override
                    public void onFail(String error) {
                        mView.dismissWaitDialog();
                        mView.showToast(error, Toast.LENGTH_SHORT);
                        mActivity.finish();
                    }
                });
    }


}
