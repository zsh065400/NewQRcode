package org.zsh.permission.callback;

/**
 * 权限请求回调接口
 *
 * @author：Administrator
 * @version:1.0
 */
public interface IHandleCallback {
	/**
	 * 授权时回调
	 *
	 * @param permission 给予授权的所有权限
	 */
	void granted(String[] permission);


	/**
	 * 拒绝时回调
	 *
	 * @param permission 被拒绝所有的权限
	 */
	void denied(String[] permission);

}
