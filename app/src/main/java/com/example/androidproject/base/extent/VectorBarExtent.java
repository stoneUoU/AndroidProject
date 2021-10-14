package com.example.androidproject.base.extent;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VectorBarExtent {
    //设置庄状态栏与ToolBar的颜色一致，低于API21无效
    public static void setToolStatusBarColor(Activity activity, View view, int color, boolean setColorBlack){
        //这两个是设置状态栏颜色
        if(Build.VERSION.SDK_INT >= 23) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color, null));
        }else if(Build.VERSION.SDK_INT >= 21){
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }
        //这个是设置文字颜色
        if(setColorBlack) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        view.setFitsSystemWindows(true);
    }

    /**
     *  小米miui6.0以上
     * @param activity
     * @param dark 是否将状态栏字体调成黑色
     * @return
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false; Window window=activity.getWindow();
        if (window != null) { Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);
                    //状态栏透明且黑色字体
                }else{ extraFlagField.invoke(window, 0, darkModeFlag);
                    //清除黑色字体
                }
                result=true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if(dark){
                        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            }catch (Exception e){ }
        }
        return result;
    }


    /**
     * 对于魅族Flayme4.0以后定义白色背景和黑色字体
     * @param window
     * @param dark 是否将字体调节成黑色
     * @return
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) { }
        }
        return result;
    }
}
