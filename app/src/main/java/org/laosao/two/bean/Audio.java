package org.laosao.two.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @version 1.0
 * @components: Audio
 * @author：赵树豪（Scout）
 * @create date：2015/10/29 7:56
 * @modified by：赵树豪
 * @dodified date：2015/10/29 7:56
 * @why ：
 */
public class Audio extends BmobObject {

	private String name;
	private BmobFile audioFile;
	private String url;

	/**
	 * 构造器，初始化表的各项数据
	 */

	public Audio(String name, BmobFile audioFile, String url) {
		this.name = name;
		this.audioFile = audioFile;
		this.url = url;
	}

	/**
	 * 自动生成的getter，setter方法
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BmobFile getPic() {
		return audioFile;
	}

	public void setPic(BmobFile pic) {
		this.audioFile = pic;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
