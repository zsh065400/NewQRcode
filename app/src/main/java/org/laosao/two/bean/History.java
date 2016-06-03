package org.laosao.two.bean;

import android.graphics.Bitmap;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class History {

    private Bitmap mBmpQrcode;
    private String mFileName;
    private String mFilePath;

    public History(Bitmap bmpQrcode, String fileName, String filePath) {
        mBmpQrcode = bmpQrcode;
        mFileName = fileName;
        mFilePath = filePath;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public Bitmap getBmpQrcode() {
        return mBmpQrcode;
    }

    public void setBmpQrcode(Bitmap bmpQrcode) {
        mBmpQrcode = bmpQrcode;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

}
