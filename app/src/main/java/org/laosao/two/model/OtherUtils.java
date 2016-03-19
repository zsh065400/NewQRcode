package org.laosao.two.model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import org.laosao.two.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import material.dialog.MaterialDialog;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class OtherUtils {

	public static void addReference(Context context, String key, int value) {
		SharedPreferences.Editor editor = context.getSharedPreferences
				(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getReference(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return preferences.getInt(key, Config.CODE_ERROR);
	}

//	public static File rootDir;
//	public static File cameraDir;
//	public static File osCamera;
//
//	public static boolean sdCardInstall = false;
//
//
//	public static void detectionSDcard() {
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//			sdCardInstall = true;
//			File root = Environment.getExternalStorageDirectory();
//			rootDir = new File(root.getAbsolutePath() + File.separator + "NewQrCode");
//			cameraDir = new File(rootDir + File.separator + "Camera");
//			osCamera = new File(root.getAbsolutePath() +
//					File.separator +
//					"DCIM" +
//					File.separator +
//					"Camera");
//			if (!rootDir.exists()) {
//				rootDir.mkdirs();
//			}
//			if (!cameraDir.exists()) {
//				cameraDir.mkdirs();
//			}
//			if (!osCamera.exists()) {
//				osCamera.mkdirs();
//			}
//		}
//	}

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

	/**
	 * 获取简单的时间戳
	 */

	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}


	/**
	 * 通过网络下载图片
	 */
	public static void getHttpBitmap(final String url, final Handler handler) {
		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {
				java.net.URL myFileURL;
				Bitmap bitmap = null;
				try {
					myFileURL = new URL(url);
					//获得连接
					HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
					//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
					conn.setConnectTimeout(10000);
					//连接设置获得数据流
					conn.setDoInput(true);
					//不使用缓存
					conn.setUseCaches(false);
					//这句可有可无，没有影响
					conn.connect();
					//得到数据流
					InputStream is = conn.getInputStream();
					//解析得到图片
					bitmap = BitmapFactory.decodeStream(is);
					//关闭数据流
					is.close();
					return bitmap;
				} catch (Exception e) {
					Log.i("LogException", e.toString());
					return null;
				}
			}

			@Override
			protected void onPostExecute(Bitmap bitmap) {
				Message msg = Message.obtain();
				if (bitmap == null) {
					msg.what = Config.CODE_ERROR;
				} else {
					msg.what = Config.CODE_YES;
					msg.obj = bitmap;
				}
				handler.sendMessage(msg);
				super.onPostExecute(bitmap);
			}
		}.execute(null, null);

	}

	public static void share(final Activity activity, final File temp, final Bitmap bitmap) {
		final MaterialDialog dialog = showWaitDialog(activity);
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(temp);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
					return true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				dialog.dismiss();
				if (aBoolean) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType(Config.IMME_PNG);
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(temp));
					activity.startActivity(
							Intent.createChooser(intent, "分享到"));
				} else {

				}
			}
		}.execute(null, null);
	}


	public static void save(final Activity activity, final Bitmap bitmap) {
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_save, null);
		final MaterialEditText etName = (MaterialEditText) view.findViewById(R.id.etSave);
		MaterialDialog dialog = new MaterialDialog(activity);
		dialog.setTitle(R.string.save);
		dialog.setView(view);
		dialog.setPositiveButton(R.string.ok, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final File save;
				if (TextUtils.isEmpty(etName.getText().toString())) {
					save = new File(SDCard.rootDir + File.separator +
							OtherUtils.getCurrentTime() +
							Config.SUFFIX_PNG);
				} else {
					save = new File(SDCard.rootDir + File.separator +
							etName.getText().toString() +
							Config.SUFFIX_PNG);
				}
//				Message msg = Message.obtain();
//				msg.what = Config.CODE_PATH;
//				msg.obj = save;
				activity.sendBroadcast(new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + save.getAbsolutePath())));
//				handler.sendMessage(msg);

				final MaterialDialog prompt = new MaterialDialog(activity);
				prompt.setTitle(R.string.title);
				prompt.setPositiveButton(R.string.ok, null);
				prompt.setNegativeButton(R.string.look, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setDataAndType(Uri.fromFile(save),
								Config.IMME_IMAGE_TYPE);
						activity.startActivity(intent);
					}
				});
				final MaterialDialog wait = showWaitDialog(activity);
				new AsyncTask<Void, Void, Boolean>() {
					@Override
					protected Boolean doInBackground(Void... params) {
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(save);
							bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
							fos.flush();
							fos.close();
							return true;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							return false;
						} catch (IOException e) {
							e.printStackTrace();
							return false;
						}
					}

					@Override
					protected void onPostExecute(Boolean aBoolean) {
						super.onPostExecute(aBoolean);
						wait.dismiss();
						if (aBoolean) {
							prompt.setMessage(activity.getString(R.string.save_path) + save.getAbsolutePath());
						} else {
							prompt.setMessage(activity.getString(R.string.fail_to_save));
							prompt.setNegativeButton(null, null);
						}
						prompt.show();
					}
				}.execute(null, null);
			}
		}).setNegativeButton(R.string.cancel, null);
		dialog.show();
	}


	public static MaterialDialog showWaitDialog(Activity context) {
		final MaterialDialog wait = new MaterialDialog(context)
				.setView(context.getLayoutInflater().inflate(R.layout.dialog_progress, null));
		wait.setCanceledOnTouchOutside(false);
		wait.show();
		return wait;
	}

	/**
	 * 自动检测更新方法
	 */
	public static void autoUpdate(final int flag, final Activity activity) {
		XiaomiUpdateAgent.setUpdateAutoPopup(false);
		XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//				switch (updateStatus) {
//					case UpdateStatus.STATUS_UPDATE:
//						L.outputDebug("检查到更新");
//						// 有更新， UpdateResponse为本次更新的详细信息
//						// 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
//						// 如果希望 SDK继续接管下载安装事宜，可调用
//						//  XiaomiUpdateAgent.arrange()
//						final View view = LayoutInflater.from(activity).inflate(R.layout.update_popup, null);
//						final TextView tvVersionCode = (TextView) view.findViewById(R.id.tvNewVersion);
//						final TextView tvUpdateContent = (TextView) view.findViewById(R.id.tvDescrption);
//
//						//设置更新信息
//						tvVersionCode.setText(tvVersionCode.getText().toString() + updateInfo.versionCode);
//						tvUpdateContent.setText(tvUpdateContent.getText().toString() + updateInfo.updateLog);
//						AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(activity);
//						builder.setTitle(activity.getString(R.string.find_new_version));
//						builder.setView(view);
////						builder.setPositiveButton(activity.getString(R.string.update), new DialogInterface.OnClickListener() {
////							@Override
////							public void onClick(DialogInterface dialog, int which) {
////								XiaomiUpdateAgent.arrange();
////							}
////						});
//						builder.setNegativeButton(activity.getString(R.string.cancel), null);
//						builder.show();
//						break;
//					case UpdateStatus.STATUS_NO_UPDATE:
//						L.outputDebug("无更新");
//						// 无更新， UpdateResponse为null
//						String versonName = null;
//						if (flag == Config.UPDATE_USER) {
//							try {
//								PackageManager manager = activity.getPackageManager();
//								PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
//								versonName = info.versionName;
//							} catch (PackageManager.NameNotFoundException e) {
//								L.outputError("获取包信息失败");
//								e.printStackTrace();
//								return;
//							}
//							Toast.makeText(activity, activity.getString(R.string.toast_new_version) +
//									versonName, Toast.LENGTH_LONG).show();
//						}
//						break;
//					case UpdateStatus.STATUS_NO_WIFI:
//						L.outputDebug("wifi下未检查到更新");
//						// 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
//					case UpdateStatus.STATUS_NO_NET:
//						// 没有网络， UpdateResponse为null
//						L.outputDebug("没有检查到网络");
//					case UpdateStatus.STATUS_FAILED:
//						L.outputDebug("与服务器通讯失败，请检查网络");
//						// 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
//						if (flag == Config.UPDATE_USER)
//							Toast.makeText(activity, activity.getString(R.string.have_not_net),
//									Toast.LENGTH_LONG).show();
//						break;
//					case UpdateStatus.STATUS_LOCAL_APP_FAILED:
//						// 检查更新获取本地安装应用信息失败， UpdateResponse为null
//						L.outputDebug("安装包解析错误");
//						if (flag == Config.UPDATE_USER)
//							Toast.makeText(activity, "检查更新获取本地安装应用信息失败",
//									Toast.LENGTH_LONG).show();
//						break;
//					default:
//						L.outputDebug("更新方法");
//						break;
//				}
			}
		});
		XiaomiUpdateAgent.update(activity);
	}


	/**
	 * 分享功能
	 *
	 * @param context       上下文
	 * @param activityTitle Activity的名字
	 * @param msgTitle      消息标题
	 * @param msgText       消息内容
	 */
	public static void shareMsg(Context context,
	                            String activityTitle,
	                            String msgTitle,
	                            String msgText) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(Config.IMME_TEXT);
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}
}
