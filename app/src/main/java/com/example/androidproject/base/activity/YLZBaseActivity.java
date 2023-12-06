package com.example.androidproject.base.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.androidproject.R;
import com.jaeger.library.StatusBarUtil;

public abstract class YLZBaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
//    private static BasePopupView loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);//通知栏所需颜色
        }*/

        /*Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }*/
        if (showGreyTheme()){
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);//灰度效果
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE,paint);
        }

        if (enableScreen()) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
//        LanguageUtil.changeAppLanguage(this, GetLocalUtil.getSavedLocal(), false);
        setContentView(setLayoutResouceId());
        initView(savedInstanceState);
        initData();
    }

    /**
     * 是否显示灰色主题
     *
     * @return
     */
    protected boolean showGreyTheme() {
        return false;
    }

    protected boolean enableScreen() {
        return true;
    }

    /**
     * 获取DataBinding对象（MVVM架构）
     * 如果有使用标题栏记的初始化标题栏要在该方法之后调用
     *
     * @param <T>
     * @return
     */
    protected <T extends ViewDataBinding> T getDataBinding() {
        return DataBindingUtil.setContentView(this, setLayoutResouceId());
    }

    /**
     * 设置布局
     *
     * @return 布局资源
     */
    protected abstract int setLayoutResouceId();

    /**
     * 初始化view
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    protected void jumpActivity(Class<?> cls) {
        jumpActivity(cls, null);
    }

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void jumpActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    protected void jumpActivityThenKill(Class<?> cls) {
        jumpActivityThenKill(cls, null);
    }

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void jumpActivityThenKill(Class<?> cls, Bundle bundle) {
        jumpActivity(cls, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    protected void jumpActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void jumpActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 初始化状态栏
     *
     * @param v_inflate
     * @param
     */
    public void initStatusBar(Activity activity, View v_inflate) {
        StatusBarUtil.setTranslucentForImageView(this, 1, v_inflate);
        StatusBarUtil.setDarkMode(this);
    }

    public void showLoading() {
//        if (loadingdialog != null) {
//            loadingdialog.dismiss();
//            loadingdialog = null;
//        }
//        loadingdialog = new XPopup.Builder(this).hasShadowBg(false).dismissOnTouchOutside(false).asCustom(new LoadingPop(this)).show();
    }

    public void setStatusBarTransparent() {
        StatusBarUtil.setDarkMode(this);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 1);
    }

    public void dismissLoading() {
//        if (loadingdialog != null) {
//            loadingdialog.dismiss();
//        }
    }
}