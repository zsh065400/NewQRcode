package org.laosao.two.utils;

import android.util.Log;

/**
 * Created by Scout.Z on 2015/8/15.
 */
public class L {

	private static final String APP_LOG_TAG = "新二维码生成器";
	public static final String CAN_RECYCLE_BITMAP = "可以回收bitmap";
	public static final String RECYCLE_BITMAP = "可以回收，调用gc";
	public static final String SHOULD_SET_VIEW = "未设置根布局或根布局中最外层布局不包含id为rootView的布局，" +
			                                             "无法设置沉浸式状态栏";


	public static void outputError(String msg) {
		Log.e(APP_LOG_TAG, msg);
	}

	public static void outputDebug(String msg) {
		Log.d(APP_LOG_TAG, msg);
	}

	public static void outputVerbose(String msg) {
		Log.v(APP_LOG_TAG, msg);
	}

	public static void outputInfo(String msg) {
		Log.i(APP_LOG_TAG, msg);
	}

	public static void outputWarm(String msg) {
		Log.w(APP_LOG_TAG, msg);
	}

}
