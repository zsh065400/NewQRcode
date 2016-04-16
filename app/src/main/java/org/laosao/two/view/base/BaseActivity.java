package org.laosao.two.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.laosao.two.model.Config;
import org.laosao.two.present.base.BasePresent;
import org.laosao.two.view.CreateActivity;
import org.zsh.mvpframework.view.IView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Scout.Z on 2015/8/12.
 */
public abstract class BaseActivity<T extends BasePresent> extends SwipeBackActivity
        implements View.OnClickListener, IView {

    protected T mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏下软键盘调试
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        setContentView(getContentView());
        mPresent = createPersent();
    }

    @Override
    protected void onPause() {
        mPresent.onPause();
        super.onPause();
    }

    protected InputMethodManager imm;

    @Override
    public void hideInput() {
        if (imm == null)
            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onResume() {
        mPresent.onReseum();
        super.onResume();
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        mPresent.onDestory();
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        mPresent.onClick(v);
    }

    @Override
    public void create(String content) {
        Bundle data = new Bundle();
        data.putString(Config.KEY_CONTENT, content);
        toOtherActivity(CreateActivity.class, data);
    }

    @Override
    public void toOtherActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showToast(String msg, int time) {
        Toast.makeText(this, msg, time).show();
    }

    @Override
    public void showToast(int resId, int time) {
        Toast.makeText(this, resId, time).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        this.dispatchTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 0, 500, 0));
        this.dispatchTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_MOVE, 600, 500, 0));
        this.dispatchTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, 600, 500, 0));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresent.onRequestPermissionsResult(permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public abstract T createPersent();

    public abstract int getContentView();
}
