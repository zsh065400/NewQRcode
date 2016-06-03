package org.laosao.two;

import android.app.Application;
import android.os.Build;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class MyApplication extends Application {
    public static int VERSION_CODE;

    @Override
    public void onCreate() {
        super.onCreate();
        VERSION_CODE = Build.VERSION.SDK_INT;
    }
}
