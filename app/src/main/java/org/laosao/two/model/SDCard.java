package org.laosao.two.model;

import android.os.Environment;

import java.io.File;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class SDCard {

	public static File rootDir;
	public static File cameraDir;
	public static File osCamera;

	public static boolean sdCardInstall = false;

	public static void detectionSDcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdCardInstall = true;
			File root = Environment.getExternalStorageDirectory();
			rootDir = new File(root.getAbsolutePath() + File.separator + "NewQrCode");
			cameraDir = new File(rootDir + File.separator + "Camera");
			osCamera = new File(root.getAbsolutePath() +
					File.separator +
					"DCIM" +
					File.separator +
					"Camera");
			if (!rootDir.exists()) {
				rootDir.mkdirs();
			}
			if (!cameraDir.exists()) {
				cameraDir.mkdirs();
			}
			if (!osCamera.exists()) {
				osCamera.mkdirs();
			}
		}
	}

}
