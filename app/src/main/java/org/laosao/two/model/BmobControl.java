package org.laosao.two.model;

import android.content.Context;

import org.laosao.two.bean.BitmapBmob;

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

    public static void uploadFile(Context context, File file, final BmobUploadCallback callBack) {
        final BmobFile bf = new BmobFile(file);
        bf.uploadblock(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                if (callBack != null) {
                    callBack.onSuccess(bf.getUrl(), bf);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (callBack != null)
                    callBack.onFail(s);
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
                    callBack.onFail(s);
                }
            }
        });
    }


    public static void findPicture(final Context context, String url, final BmobQueryCallback callback) {
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
