package org.laosao.two.present;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import org.laosao.two.MyApplication;
import org.laosao.two.R;
import org.laosao.two.model.Config;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.PatronActivity;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class PatronPresent extends BasePresent<PatronActivity> {

    public PatronPresent(Activity activity, PatronActivity view) {
        super(activity, view);
    }

    @Override
    public void onClick(View v) {
        if (MyApplication.VERSION_CODE == Build.VERSION_CODES.M)
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        else
            saveZfb();
    }

    @Override
    public void granted(String[] permission) {
        saveZfb();
    }

    @Override
    public void denied(String[] permission) {
        mView.showToast(mActivity.getString(R.string.write_permission_denied), Toast.LENGTH_LONG);
    }

    private void saveZfb() {
        Bitmap bitmap = mView.getZfb();
        File save = new File(SDCard.rootDir + File.separator +
                "支付宝赞助" +
                Config.SUFFIX_PNG);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(save);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            mActivity.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + save.getAbsolutePath())));
            mView.showToast("已保存到NewQrcode目录下", Toast.LENGTH_LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
