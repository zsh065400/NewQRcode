package org.zsh.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import org.zsh.permission.callback.IHandleCallback;
import org.zsh.permission.callback.IRationale;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求类
 *
 * @author zhaoshuhao
 * @version 2.0
 */
public class Permission {
	private static final String TAG = Permission.class.getSimpleName();
	private IHandleCallback mCallback;
	private IRationale mRationale;
	private final int requestCode = 0x222;

	static class PermissionHolder {
		public static final Permission sInstance = new Permission();
	}

	/**
	 * 设置被拒绝权限的提示信息
	 * <p>当权限被拒绝一次后再次申请时</p>
	 * <p>小米等第三方ROM可能无效。</p>
	 *
	 * @param rationable 提示回调
	 */
	public void setRationable(IRationale rationable) {
		this.mRationale = rationable;
	}

	public static Permission getInstance() {
		return PermissionHolder.sInstance;
	}

	private Permission() {
	}

	private String[] listToArray(List list) {
		int length = list.size();
		String[] s = new String[length];
		return (String[]) list.toArray(s);
	}

	/**
	 * 执行请求权限操作
	 *
	 * @param callback    设置回调接口
	 * @param permissions 请求的权限
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public void request(IHandleCallback callback, Activity activity,
	                    @NonNull String... permissions) {
		this.mCallback = callback;
		if (permissions == null || permissions.length == 0)
			throw new IllegalArgumentException("requested permission is null, method can't to do");
		if (permissions.length == 1) {
			one(activity, permissions[0]);
		} else {
			many(activity, permissions);
		}
	}

	/**
	 * 请求多个权限
	 *
	 * @param permissions 多个权限
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public void many(Activity activity, String... permissions) {
		Log.d(TAG, "many: permissions have " + permissions.length + " permissions");
//		需要请求的权限
		List<String> need = new ArrayList();
//		拒绝过的权限
		List<String> denied = new ArrayList<>();
//		已经获得授权的权限
		List<String> granted = new ArrayList<>();
		for (String permission : permissions) {
			if (!checkState(activity, permission)) {
				need.add(permission);
				if (checkShouldShowRationale(activity, permission))
					denied.add(permission);
			} else {
				granted.add(permission);
			}
		}
		if (!denied.isEmpty()) {
			if (mRationale != null)
				mRationale.showRationale(listToArray(denied));
		}
		//判断有无需要请求的权限
		if (!need.isEmpty()) {
			activity.requestPermissions(listToArray(need), requestCode);
		}
		if (!granted.isEmpty()) {
			mCallback.granted(listToArray(granted));
		}
	}

	/**
	 * <p> 请求指定权限</p>
	 * 建议只在需要的位置请求指定权限，且不要一次请求多个权限，造成用户体验的不友好
	 *
	 * @param permission 指定权限
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public void one(Activity activity, String permission) {
		Log.d(TAG, "one: the permission is--->" + permission);
		boolean state = checkState(activity, permission);
		String[] s = new String[]{permission};
		if (!state) {
			//为true，表明用户拒绝过
			if (checkShouldShowRationale(activity, permission)) {
				if (mRationale != null)
					//请求提示信息
					mRationale.showRationale(s);
			}
			//请求权限
			activity.requestPermissions(s, requestCode);
		} else {
			//已经授权则回调接口
			mCallback.granted(s);
		}
	}

	/**
	 * 检查权限的授权状态
	 *
	 * @param permission 被检查的权限
	 * @return true为授权，false为未授权
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public boolean checkState(Activity activity, String permission) {
		int granted = activity.checkSelfPermission(permission);
		boolean state = granted == PackageManager.PERMISSION_GRANTED ? true : false;
		Log.d(TAG, "checkState ---> " + permission + " granted: " + state);
		return state;
	}

	/**
	 * 检查权限是否应该显示提示信息
	 *
	 * @param permission 被检查的权限
	 * @return 拒绝过返回true，否则返回false
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public boolean checkShouldShowRationale(Activity activity, String permission) {
		boolean b = activity.shouldShowRequestPermissionRationale(permission);
		Log.d(TAG, "checkShouldShowRationale ---> " + permission + " denied : " + b);
		return b;
	}

	/**
	 * 通知结果，回调接口
	 *
	 * @param permissions
	 * @param grantResults
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
		Log.d(TAG, "onRequestPermissionsResult: have " + permissions.length + " permissions");
		List<String> d = new ArrayList<>();
		List<String> g = new ArrayList<>();
		int i = 0;
		for (String permission : permissions) {
			final int grantResult = grantResults[i];
			if (grantResult == PackageManager.PERMISSION_GRANTED)
				g.add(permission);
			else
				d.add(permission);
			i++;
		}
		if (!g.isEmpty() && mCallback != null) {
			mCallback.granted(listToArray(g));
		}
		if (!d.isEmpty() && mCallback != null) {
			mCallback.denied(listToArray(d));
		}
		recycle();
	}

	/**
	 * 清理内存，解决重复请求问题
	 */
	private void recycle() {
		mCallback = null;
		mRationale = null;
	}

}
