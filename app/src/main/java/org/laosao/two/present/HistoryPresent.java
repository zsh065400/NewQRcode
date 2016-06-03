package org.laosao.two.present;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import org.laosao.two.MyApplication;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.HistoryActivity;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class HistoryPresent extends BasePresent<HistoryActivity> {
    private boolean mIsHavePermission = false;

    public HistoryPresent(Activity activity, HistoryActivity view) {
        super(activity, view);
    }

    @Override
    public void onCreate() {
        if (MyApplication.VERSION_CODE == Build.VERSION_CODES.M) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            mIsHavePermission = true;
        }
        if (!mIsHavePermission) {
            mView.tipNoPermission();
            mActivity.finish();
            return;
        }
        mView.showWaitDialog();
        super.onCreate();
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }

    @Override
    public void onReseum() {
        super.onReseum();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(permissions, grantResults);
    }

    @Override
    public void granted(String[] permission) {
        mIsHavePermission = true;
    }

    @Override
    public void denied(String[] permission) {
        mView.tipNoPermission();
        mActivity.finish();
    }
}
