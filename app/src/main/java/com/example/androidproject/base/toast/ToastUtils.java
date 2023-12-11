package com.example.androidproject.base.toast;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created By   : LiLinXiang
 * Created Date : 2017-5-8
 * Introduction : 吐司相关工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;
    private static Context sContext;

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static void init(Context context, boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
        sContext = context;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text    文本
     */
    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId   资源Id
     */
    public static void showShortToastSafe(final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToastSafe(final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format  格式
     * @param args    参数
     */
    public static void showShortToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text    文本
     */
    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId   资源Id
     */
    public static void showLongToastSafe(final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToastSafe(final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format  格式
     * @param args    参数
     */
    public static void showLongToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text    文本
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId   资源Id
     */
    public static void showShortToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToast(int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format  格式
     * @param args    参数
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text    文本
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId   资源Id
     */
    public static void showLongToast(int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToast(int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format  格式
     * @param args    参数
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (sContext == null) {
                throw new NullPointerException("sContext为空");
            } else {
                sToast = Toast.makeText(sContext.getApplicationContext(), null, duration);
                sToast.setText(text);
                sToast.show();
            }
        } else {
            if (isJumpWhenMore) cancelToast();
            if (sToast == null) {
                if (sContext == null) {
                    throw new NullPointerException("sContext为空");
                } else {
                    sToast = Toast.makeText(sContext.getApplicationContext(), text, duration);
                }
            } else {
                sToast.setText(text);
                sToast.setDuration(duration);
            }
            sToast.show();
        }
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(int resId, int duration) {
        if(sContext == null) {
            throw new NullPointerException("sContext为空");
        }else{
            showToast(sContext.getResources().getText(resId).toString(), duration);
        }

    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(int resId, int duration, Object... args) {
        if(sContext == null) {
            throw new NullPointerException("sContext为空");
        }else {
            showToast(String.format(sContext.getResources().getString(resId), args), duration);
        }

    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}