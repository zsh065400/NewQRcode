package org.laosao.two.biz;

import android.content.Context;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.UploadBatchListener;
import com.bmob.btp.callback.UploadListener;

import org.laosao.two.Config;
import org.laosao.two.bean.BitmapBmob;
import org.laosao.two.bean.PersonIdCard;
import org.laosao.two.utils.L;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class BmobControl {
	private static final int ERROR_TO_UPLOAD = 9003;
	private static final int FAIL_TO_UPLOAD = 9004;
	private static final int FILE_TOO_BIG = 9007;
	private static final int NET_TIME_OUT = 9010;
	private static final int NET_NOT_HAVE = 9016;


	//新版文件上传
	public static void newUploadImage(Context context, String filePath, final BmobUploadCallback callBack) {
		BmobProFile.getInstance(context).upload(filePath, new UploadListener() {
			@Override
			public void onSuccess(String fileName, String url, BmobFile bmobFile) {
				if (callBack != null) {
					callBack.onSuccess(bmobFile.getUrl(), bmobFile);
				}
				L.outputDebug("success to upload picture file");
			}

			@Override
			public void onProgress(int i) {
				L.outputDebug("upload progress" + i);
			}

			@Override
			public void onError(int i, String s) {
				L.outputDebug("fail to upload : " + s);
				if (callBack != null) {
					String error = null;
					switch (i) {
						case ERROR_TO_UPLOAD:
							error = "上传文件出错";
							break;
						case FAIL_TO_UPLOAD:
							error = "上传文件失败";
							break;
						case FILE_TOO_BIG:
							error = "文件大小超过10M";
							break;
						case NET_TIME_OUT:
							error = "网络连接超时";
							break;
						case NET_NOT_HAVE:
							error = "无网络连接，请检查";
							break;
						default:
							error = "上传过程出错";
							break;
					}
					callBack.onFail(error);
				}
			}
		});
	}

	/**
	 * 通过新版文件管理的唯一文件名，进行文件下载操作。
	 *
	 * @param context  程序上下文
	 * @param fileName 通过BmobProFile类进行上传操作后返回的唯一fileName
	 *                 不能是BmobFile上传返回的fileName
	 */
	public static void download(Context context, String fileName) {
		BmobProFile.getInstance(context).download(fileName, new DownloadListener() {
			@Override
			public void onSuccess(String filePath) {

			}

			@Override
			public void onProgress(String s, int i) {

			}

			@Override
			public void onError(int i, String s) {

			}
		});
	}

	/**
	 * 批量上传文件，用于生成多图二维码
	 *
	 * @param context   程序上下文
	 * @param filePaths 包含所有用于上传文件的路径数组
	 */
	public static void uploadBatch(Context context, String[] filePaths) {
		BmobProFile.getInstance(context).uploadBatch(filePaths, new UploadBatchListener() {
			@Override
			public void onSuccess(boolean isFinish, String[] fileNames, String[] urls, BmobFile[] files) {

			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {

			}

			@Override
			public void onError(int i, String s) {

			}
		});
	}

	//旧版文件上传
	public static void uploadImage(Context context, final BmobUploadCallback callBack, File file) {
		final BmobFile img = new BmobFile(file);
		img.upload(context, new UploadFileListener() {
			@Override
			public void onSuccess() {
				if (callBack != null) {
					callBack.onSuccess(img.getUrl(), img);
				}
			}

			@Override
			public void onFailure(int i, String s) {
				if (callBack != null) {
					String error = null;
					switch (i) {
						case ERROR_TO_UPLOAD:
							error = "上传文件出错";
							break;
						case FAIL_TO_UPLOAD:
							error = "上传文件失败";
							break;
						case FILE_TOO_BIG:
							error = "文件大小超过10M";
							break;
						case NET_TIME_OUT:
							error = "网络连接超时";
							break;
						case NET_NOT_HAVE:
							error = "无网络连接，请检查";
							break;
						default:
							error = "上传过程出错";
							break;
					}
					callBack.onFail(error);
				}
			}
		});
	}

	public static void insertObject(Context context, final BmobSaveCallback callBack, final BmobObject obj) {
		obj.save(context, new SaveListener() {
			@Override
			public void onSuccess() {
				if (callBack != null) {
					callBack.onSuccess();
				}
			}

			@Override
			public void onFailure(int i, String s) {
				if (callBack != null) {
					String error = null;
					switch (i) {
						case NET_TIME_OUT:
							error = "网络连接超时";
							break;
						case NET_NOT_HAVE:
							error = "无网络连接，请检查";
							break;
						default:
							error = "未知错误";
							break;
					}
					callBack.onFail(error);
				}
			}
		});
	}

	public static void queryNetCard(final Context context, String url, final BmobQueryCallback callback) {
		BmobQuery<PersonIdCard> query = new BmobQuery<>();
		query.addWhereEqualTo(Config.URL_COLUMN, url);
		query.findObjects(context, new FindListener<PersonIdCard>() {
			@Override
			public void onSuccess(List<PersonIdCard> list) {
				if (list.size() == 0) {
					if (callback != null) {
						callback.queryZero();
					}
					return;
				}
				if (callback != null) {
					callback.onSuccess(list.get(0));
				}
			}

			@Override
			public void onError(int i, String s) {
				if (callback != null) {
					String error = null;
					switch (i) {
						case NET_TIME_OUT:
							error = "网络连接超时";
							break;
						case NET_NOT_HAVE:
							error = "无网络连接，请检查";
							break;
						default:
							error = "未知错误";
							break;
					}
					callback.onFail(error);
				}
			}
		});
	}

	public static void queryPic(final Context context, String url, final BmobQueryCallback callback) {
		BmobQuery<BitmapBmob> query = new BmobQuery<>();
		query.addWhereEqualTo(Config.URL_COLUMN, url);
		query.findObjects(context, new FindListener<BitmapBmob>() {
			@Override
			public void onSuccess(List<BitmapBmob> list) {
				if (list.size() == 0) {
					if (callback != null) {
						callback.queryZero();
					}
					return;
				}
				if (callback != null) {
					callback.onSuccess(list.get(0));
				}
			}

			@Override
			public void onError(int i, String s) {
				if (callback != null) {
					callback.onFail(s);
				}
			}
		});
	}

	public interface BmobDownloadCallback extends FailCallback {
		void onSuccess();
	}

	public interface BmobQueryCallback extends FailCallback {
		void queryZero();

		void onSuccess(BmobObject object);
	}

	public interface BmobUploadCallback extends FailCallback {
		void onSuccess(String url, BmobFile img);

	}

	public interface BmobSaveCallback extends FailCallback {
		void onSuccess();

	}

	public interface FailCallback {
		void onFail(String error);
	}

}
