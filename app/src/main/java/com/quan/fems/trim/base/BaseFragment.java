package com.quan.fems.trim.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BaseFragment extends Fragment {
    protected boolean flag = false;
    protected ImageLoadingListener mImageLoadingListener = new ImageDisplayListener();
    protected DisplayImageOptions options = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //判断是否联网
        if (!isNetwork(getActivity())) {
            Toast.makeText(getActivity(), "请检查网络！", Toast.LENGTH_SHORT).show();
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
        return container;
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
     * @功能：图片的加载使用
     */
    private static class ImageDisplayListener extends SimpleImageLoadingListener {
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
}
