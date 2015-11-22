package org.laosao.two.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Scout.Z on 2015/8/16.
 */
public class T {
	public static void showShortToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showShortToast(Context context, int id) {
		Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(Context context, int id) {
		Toast.makeText(context, context.getString(id), Toast.LENGTH_LONG).show();
	}
}
