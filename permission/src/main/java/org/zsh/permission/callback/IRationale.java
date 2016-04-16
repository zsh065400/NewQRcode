package org.zsh.permission.callback;

/**
 * 处理被拒绝权限，提示其信息
 *
 * @author：Administrator
 * @version:1.0
 */
public interface IRationale {

	/**
	 * 用于提示权限信息
	 *
	 * @param permissions 被拒绝过的所有权限
	 */
	void showRationale(String[] permissions);

}
