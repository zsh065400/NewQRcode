package org.laosao.two;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.laosao.two.utils.L;

import java.io.File;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class Config extends org.laosao.two.utils.T {
	public static final String URL_COLUMN = "url";

	//编码模式
	public static final char QRCODE_ENCODING_MODE = 'B';
	//容错等级
	public static final char QRCODE_ERROR_CORRECT = 'M';
	//编码方式
	public static final String ENCDOING = "utf-8";
	//Bmob通信ID
	public static final String BMOB_SERVER_APP_ID = "a87bed9b1b14a484df1041ae578cb6b2";

	//	public static final String BMOB_SERVER_APP_ID = "ceaacb67f642d51a33096f1e19f725ef";


	public static File rootDir;
	public static File cameraDir;
	public static File osCamera;

	public static boolean sdCardInstall = false;

	//key
	public static final String KEY_SPLASH = "splash";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_SCAN_NET_CARD = "065400zsh";
	public static final String KEY_SCAN_PICTURE = "http://file.bmob.cn/";
	public static final String KEY_SCAN_WIFI = "WIFI:T:";
	public static final String KEY_RESULT = "result";

	//special
	public static final String EMPTY_STR = "";
	public static final String NEW_LINE = "\n";
	public static final String COMMA = "，";
	public static final String SUFFIX_PNG = ".png";
	public static final String IMME_IMAGE_TYPE = "image/*";
	public static final String IMME_PNG = "image/png";
	public static final String IMME_AUDIO = "audio/*";
	public static final String IMME_RECORDER = "audio/amr";

	public static final int CODE_ERROR = -1;
	public static final int CODE_YES = 1;
	public static final int CODE_PATH = 2;
	public static final int CODE_ADD_LOGO = 3;

	public static final int REQ_OPEN_CAMERA = 10005;
	public static final int REQ_OPEN_IMG = 10006;
	public static final int REQ_CROP_IMG = 10007;


	public static final int BITMAP_THUMBNAIL_HEIGHT = 800;
	public static final int BITMAP_THUMBNAIL_WIDTH = 650;

	public static final int UPDATE_AUTO = 0x789;
	public static final int UPDATE_USER = 0x788;

	public static void addReference(Context context, String key, int value) {
		SharedPreferences.Editor editor = context.getSharedPreferences
												  (context.getPackageName(), Context.MODE_PRIVATE)
										  .edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getReference(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return preferences.getInt(key, CODE_ERROR);
	}
	
	
	// TODO: 2015/8/17 字符串 
	public static void detectionSDcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdCardInstall = true;
			// TODO: 2015/8/18 文件夹
			File root = Environment.getExternalStorageDirectory();
			rootDir = new File(root.getAbsolutePath() + File.separator + "NewQrCode");
			cameraDir = new File(rootDir + File.separator + "Camera");
			osCamera = new File(root.getAbsolutePath() +
								File.separator +
								"DCIM" +
								File.separator +
								"Camera");
			if (!rootDir.exists()) {
				rootDir.mkdirs();
			}
			if (!cameraDir.exists()) {
				cameraDir.mkdirs();
			}
			if (!osCamera.exists()) {
				osCamera.mkdirs();
			}
		}
	}

	/**
	 * 设置状态栏透明（沉浸式状态栏）
	 */
	@TargetApi(19)
	public static void setLayoutTransparentStatus(Activity activity,
												  int resId) {
		setTranslucentStatus(true, activity);
		// 获取activity状态栏高度
		int statusHeight = getStatusHeight(activity);
		// 获取颜色对象
		Drawable drawable = activity.getResources().getDrawable(resId);
		// 设置背景颜色
		activity.getWindow().setBackgroundDrawable(drawable);
		// 获取布局属性设置布局外上边距
		ViewGroup rl = (ViewGroup) activity.findViewById(R.id.rootView);
		FrameLayout.LayoutParams lp = null;
		try {
			lp = (FrameLayout.LayoutParams) rl.getLayoutParams();
		} catch (Exception e) {
			L.outputError(L.SHOULD_SET_VIEW);
		}
		lp.setMargins(0, statusHeight, 0, 0);
		rl.setLayoutParams(lp);
	}

	@TargetApi(19)
	public static void setTranslucentStatus(boolean on, Activity activity) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
										  .get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}
}
