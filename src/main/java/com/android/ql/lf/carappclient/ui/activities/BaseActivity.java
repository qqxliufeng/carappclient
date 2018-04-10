package com.android.ql.lf.carappclient.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.ql.lf.carappclient.R;
import com.android.ql.lf.carappclient.interfaces.INetDataPresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Administrator on 2017/10/17 0017.
 *
 * @author lf
 */

public abstract class BaseActivity extends AppCompatActivity implements INetDataPresenter, SwipeBackActivityBase {

    private SwipeBackActivityHelper swipeBackActivityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initSwipe();
        initView();
    }

    private void initSwipe() {
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        setSwipeBackEnable(true);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && swipeBackActivityHelper != null) {
            return swipeBackActivityHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public void onBackPressed() {
        scrollToFinishActivity();
    }


    public int getStatusBarColor() {
        return ContextCompat.getColor(this, R.color.colorPrimary);
    }

    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getStatusBarColor());
        }
    }


    public int getStatusHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    /**
     * 布局文件
     *
     * @return 资源ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    @Override
    public void onRequestStart(int requestID) {
    }

    @Override
    public void onRequestFail(int requestID, @NotNull Throwable e) {
    }

    @Override
    public <T> void onRequestSuccess(int requestID, T result) {
    }

    @Override
    public void onRequestEnd(int requestID) {
    }
}
