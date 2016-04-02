package org.laosao.two.model;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ScrollView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public class ImageUtils {


	public static void openCamera(Activity activity, String photoPath) {
		Uri fileUri = Uri.fromFile(new File(photoPath));
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		activity.startActivityForResult(camera, Config.REQ_OPEN_CAMERA);
	}


	/**
	 * @param activity
	 */
	public static void openGallery(Activity activity) {
		Intent image = new Intent();
		image.setAction(Intent.ACTION_GET_CONTENT);
		image.setType(Config.IMME_IMAGE_TYPE);
		if (image.resolveActivity(activity.getPackageManager()) == null) {
			image.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		activity.startActivityForResult(image, Config.REQ_OPEN_IMG);
	}


	/**
	 * 裁剪图片
	 *
	 * @param uri
	 */
	public static void cropImage(Uri uri, Activity activity, String path,
	                             final int width, final int height) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, Config.IMME_IMAGE_TYPE);
		intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
		intent.putExtra("aspectY", 1);// x:y=1:1
		intent.putExtra("outputX", width);//图片输出大小
		intent.putExtra("outputY", height);
		intent.putExtra("outputFormat", "PNG");// 返回格式
		Uri fileUri = Uri.fromFile(new File(path));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		activity.startActivityForResult(intent, Config.REQ_CROP_IMG);
	}

	/**
	 * 截取scrollview的屏幕
	 *
	 * @param scrollView
	 * @return
	 */
	public static Bitmap getBitmapByView(ScrollView scrollView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollview实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			scrollView.getChildAt(i).setBackgroundColor(
					Color.parseColor("#ffffff"));
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		return bitmap;
	}


	/**
	 * 压缩图片
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, int kb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > kb) {
			// 重置baos
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减少10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}


	/**
	 * 根据宽高按比例缩放
	 *
	 * @param path   目标文件路径
	 * @param width  新宽度
	 * @param height 新高度
	 * @return
	 */
	public static Bitmap getBitmapForPictureQrcode(String path, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
//		//默认
		bitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false;
		int be = 0;
		int bw = options.outWidth;
		int bh = options.outHeight;
		//横向图片，宽大于高
		while (bw > width || bh > height) {
			be += 2;
			bw /= 2;
			bh /= 2;
		}
		if (be == 0) be = 1;
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(path, options);
		return compressImage(bitmap, 200);
	}

	public static Bitmap getLogo(String path, int size) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
//		//默认
		bitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false;
		int be = 1;
		int s = options.outWidth;
		int scale = s / size;
		//横向图片，宽大于高
		while (scale > be) {
			be = scale;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	/**
	 * 根据Uri内容获取媒体文件路径
	 *
	 * @param context 上下文
	 * @param uri     文件uri
	 * @return 文件路径
	 */
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	                                   String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

}
