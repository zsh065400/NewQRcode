package org.laosao.two.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class UserFile extends BmobObject {

	private String mFileName;
	private String mUrl;
	private BmobFile mBmobFile;

	public UserFile(String fileName, String url, BmobFile bmobFile) {
		mFileName = fileName;
		mUrl = url;
		mBmobFile = bmobFile;
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String fileName) {
		mFileName = fileName;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public BmobFile getBmobFile() {
		return mBmobFile;
	}

	public void setBmobFile(BmobFile bmobFile) {
		mBmobFile = bmobFile;
	}
}
