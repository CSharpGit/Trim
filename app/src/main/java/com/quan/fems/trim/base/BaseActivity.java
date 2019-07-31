package com.quan.fems.trim.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.quan.fems.trim.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    protected boolean flag = false;
    protected ImageLoadingListener mImageLoadingListener = new ImageDisplayListener();
    protected DisplayImageOptions options = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setStatusBarColor();//设置状态栏颜色
        //判断是否联网
        if (!isNetwork(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "请检查网络！", Toast.LENGTH_SHORT).show();
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
//		.displayer(new RoundedBitmapDisplayer(20))//圆角
                .build();
    }

    /**
     * 通用方法:判断是否有网络
     *
     * @param context
     */
    protected boolean isNetwork(final Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager.getActiveNetworkInfo() != null) {
            flag = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 通用方法：设置网络（没有网络的情况下）
     *
     * @param context
     */
    protected boolean setNetwork(final Context context) {
        flag = isNetwork(context);
        //若没有网络，提示是否开启网络
        if (!flag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("设置网络");
            builder.setMessage("网络错误，请设置网络");
            builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                    System.out.println("currentapiVersion = " + currentapiVersion);
                    Intent intent;
                    if (currentapiVersion < 11) {
                        intent = new Intent();
                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                    } else {
                        //3.0以后
                        //intent = new Intent( android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    }
                    context.startActivity(intent);
                    finish();
//                System.exit(0);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    flag = false;
                }
            });
            builder.create().show();
            return flag;
        }
        return flag;
    }

    /**
     * @功能：图片的加载使用
     */
    public static class ImageDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    /**
     * @功能：设置状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#fe9900"));
        }
    }
}
