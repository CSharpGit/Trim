package com.quan.fems.trim.untils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class ScreenInfoUtils {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 获取屏幕真实高度
     *
     * @param context Context
     * @return Real Height
     */
    public static int getRealHeight(Context context) {
        Display display = getDisplay(context);
        if (display == null) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕真实宽度
     *
     * @param context Context
     * @return Real Width
     */
    public static int getRealWidth(Context context) {
        Display display = getDisplay(context);
        if (display == null) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context mContext) {
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获取标题栏高度
     */
    public static int getActionBarHeight(Context mContext) {
        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight(Context mContext) {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    /**
     * 获取屏幕指标
     *
     * @param context Context for get Resources
     * @return DisplayMetrics
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕显示信息
     *
     * @param context Context for get WindowManager
     * @return Display
     */
    private static Display getDisplay(Context context) {
        WindowManager wm;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            wm = activity.getWindowManager();
        } else {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (wm != null) {
            return wm.getDefaultDisplay();
        }
        return null;
    }

    /**
     * 获取屏幕密度比例，1dip显示的像素点
     */
    private static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    /**
     * 获取屏幕密度
     */
    private static int getDpi(Context context) {
        return getDisplayMetrics(context).densityDpi;
    }

    public static void printScreenInfo(Context mContext){
        int screenWidth=getScreenWidth(mContext);
        int screenHeight=getScreenHeight(mContext);
        int screenDpi=getDpi(mContext);
        float screenDensity=getDensity(mContext);
        int realWidth=getRealWidth(mContext);
        int realHeight=getRealHeight(mContext);
        int statusBarHeight=getStatusBarHeight(mContext);
        int actionBarHeight=getActionBarHeight(mContext);
        int navigationBarHeight=getNavigationBarHeight(mContext);
        System.out.print("屏幕相关信息："+"\n屏幕宽度："+screenWidth+"\n屏幕高度："+screenHeight+"\n屏幕密度："+screenDpi+"\n屏幕密度比例："+screenDensity+"\n屏幕真实宽度："+realWidth+"\n屏幕真实高度："+realHeight+"\n状态栏高度："+statusBarHeight+"\n标题栏高度："+actionBarHeight+"\n导航栏高度："+navigationBarHeight+"\n");
    }
}
