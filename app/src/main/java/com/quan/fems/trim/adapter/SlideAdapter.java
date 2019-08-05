package com.quan.fems.trim.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.quan.fems.trim.R;
import com.quan.fems.trim.bean.SlideBean;
import com.quan.fems.trim.server.Commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SlideAdapter extends PagerAdapter {
    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private List<SlideBean> listData;
    private Context mContext;
    protected DisplayImageOptions options;
    public SlideAdapter(Context context, List<SlideBean> listData){
        this.listData=listData;
        this.mContext =context;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))//圆角
                .build();
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SlideBean bean = (SlideBean)listData.get(position);
        ImageView img = new ImageView(mContext);
        System.out.println("图片路径："+Commons.WEB_URL+Commons.IMG_DIR+bean.imgurl);
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+bean.imgurl, img, options, animateFirstListener);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(img);
        return img;
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
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
