package org.laosao.two.model;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class Config {
	public static final String URL_COLUMN = "url";
	//编码方式
	public static final String ENCODING_UTF_8 = "utf-8";
	//Bmob通信ID
	public static final String BMOB_SERVER_APP_ID = "a87bed9b1b14a484df1041ae578cb6b2";

	//	public static final String BMOB_SERVER_APP_ID = "ceaacb67f642d51a33096f1e19f725ef";

	//key
	public static final String KEY_CONTENT = "content";
	public static final String KEY_SCAN_NET_CARD = "065400zsh";
	public static final String KEY_SCAN_PICTURE = "http://file.bmob.cn/";
	public static final String KEY_SCAN_FILE = "file.bmob.cn/";
	public static final String KEY_SCAN_WIFI = "WIFI:T:";
	public static final String KEY_RESULT = "result";

	//special
	public static final String EMPTY_STR = "";
	public static final String NEW_LINE = "\n";
	public static final String SUFFIX_PNG = ".png";
	public static final String IMME_IMAGE_TYPE = "image/*";
	public static final String IMME_PNG = "image/png";
	public static final String IMME_AUDIO = "audio/*";
	public static final String IMME_TEXT = "text/plain";
	public static final String IMME_ALL = "*/*";

	public static final int CODE_ERROR = -1;
	public static final int CODE_YES = 1;

	public static final int REQ_OPEN_CAMERA = 10005;
	public static final int REQ_OPEN_IMG = 10006;
	public static final int REQ_CROP_IMG = 10007;

	public static final int REQ_FILE_CODE = 0x1113;
	public static final int REQ_RECORDER_CODE = 0x1114;

	public static final int UPDATE_AUTO = 0x789;
	public static final int UPDATE_USER = 0x788;

}
