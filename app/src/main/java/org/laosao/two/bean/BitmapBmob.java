package org.laosao.two.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Bmob云服务表，图片二维码上传
 * Created by lenovo on 2015/1/24.
 *
 * @author Mr.zhao
 * @version 1.0
 */
public class BitmapBmob extends BmobObject {
	private String name;
	private BmobFile pic;
	private String url;

	/**
	 * 构造器，初始化表的各项数据
	 */

	public BitmapBmob(String name, BmobFile pic, String url) {
		this.name = name;
		this.pic = pic;
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
		return pic;
	}

	public void setPic(BmobFile pic) {
		this.pic = pic;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
