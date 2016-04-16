package org.laosao.two;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BmobConfig config = new BmobConfig.Builder()
                .setConnectTimeout(10)
                .setBlockSize(100 * 1024)
                .build();
        Bmob.getInstance().initConfig(config);
    }
}
