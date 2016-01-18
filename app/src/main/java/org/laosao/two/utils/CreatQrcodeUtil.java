package org.laosao.two.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.swetake.util.Qrcode;

import org.laosao.two.Config;


/**
 * Created by Scout.Z on 2015/8/12.
 * 二维码生成工具类
 */
public class CreatQrcodeUtil {
	//所需内容
	private static String mContent;
	private static int mColor;

	private static Bitmap mBitmap;
	//变换幅度
	private static int iPadding = 16;
	private static float strokeWidth = 0.1F;

	//279  4 25字以内   335 7 55字及以内      455 10 90字以内    559 14 140字以内
	//二维码大小和版本
	public static int imgSize = 355;
	private static int qrVersion = 7;

	public static final int LENGTH_LV0 = 0;
	public static final int LENGTH_LV1 = 1;
	public static final int LENGTH_LV2 = 2;
	public static final int LENGTH_LV3 = 3;
	public static final int LENGTH_LV4 = 4;

	private CreatQrcodeUtil() {
	}

	private static void init(int color, String content, int lv) {
		mColor = color;
		mContent = content;
		switch (lv) {
			case LENGTH_LV0:
				imgSize = 272;
				qrVersion = 4;
				break;
			case LENGTH_LV1:
				//default
				break;
			case LENGTH_LV2:
				imgSize = 445;
				qrVersion = 10;
				break;
			case LENGTH_LV3:
				imgSize = 550;
				qrVersion = 14;
				break;
			case LENGTH_LV4:
				imgSize = 720;
				qrVersion = 20;
				break;
		}
	}

	/**
	 * 生成方法
	 *
	 * @param content 生成内容
	 * @param color   生成颜色
	 * @param lv      字数等级
	 */
	public static void createQRcode(int color, String content, int lv, final Handler handler) {
		init(color, content, lv);
		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				mBitmap = Bitmap.createBitmap(imgSize, imgSize, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(mBitmap);

//				try {
				Qrcode qrcode = new Qrcode();
				qrcode.setQrcodeErrorCorrect(Config.QRCODE_ERROR_CORRECT);
				qrcode.setQrcodeEncodeMode(Config.QRCODE_ENCODING_MODE);
				qrcode.setQrcodeVersion(qrVersion);
				byte[] bytesEncoding;
				boolean[][] bEncoding;
				try {
					bytesEncoding = mContent.getBytes(Config.ENCODING_UTF_8);
					bEncoding = qrcode.calQrcode(bytesEncoding);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

				canvas.drawColor(Color.WHITE);
				Paint paint = new Paint();
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(mColor);
				paint.setStrokeWidth(strokeWidth);
				for (int i = 0; i < bEncoding.length; i++) {
					for (int j = 0; j < bEncoding.length; j++) {
						if (bEncoding[j][i]) {
							canvas.drawRect(new Rect(iPadding + j * 7 + 6,
									iPadding + i * 7 + 6, iPadding + j * 7
									+ 6 + 7, iPadding + i * 7 + 6
									+ 7), paint);
						}
					}
				}

//				} catch (Exception e) {
//					e.printStackTrace();
//					return null;
//				}
				return mBitmap;
			}

			@Override
			protected void onPostExecute(Bitmap bitmap) {
				super.onPostExecute(bitmap);
				Message msg = Message.obtain();
				if (bitmap == null) {
					msg.what = Config.CODE_ERROR;

				} else {
					msg.obj = bitmap;
					msg.what = Config.CODE_YES;
				}
				handler.sendMessage(msg);
			}
		}.execute(null, null);

	}
}