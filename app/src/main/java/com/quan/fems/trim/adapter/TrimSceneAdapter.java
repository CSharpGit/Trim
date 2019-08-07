package com.quan.fems.trim.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.quan.fems.trim.R;
import com.quan.fems.trim.bean.TrimSceneBean;
import com.quan.fems.trim.server.Commons;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TrimSceneAdapter extends RecyclerView.Adapter<TrimSceneAdapter.ViewHolder> {
    protected DisplayImageOptions options;
    protected ImageLoadingListener mImageLoadingListener=new ImageDisplayListener();
    private List<TrimSceneBean> list;
    private OnItemClickListener mOnItemClickListener;
    public TrimSceneAdapter(List<TrimSceneBean> list) {
        this.list = list;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trim_scene_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTrimStyle.setText(list.get(position).styleName);
        holder.mTrimSceneDes.setText(list.get(position).descrip);
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+list.get(position).imgurl,holder.mImg,options,mImageLoadingListener);
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,position,list);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemClick(v,position,list);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTrimStyle,mTrimSceneDes;
        ImageView mImg;
        ViewHolder(View itemView) {
            super(itemView);
            mTrimStyle = itemView.findViewById(R.id.trim_scene_style_text);
            mTrimSceneDes = itemView.findViewById(R.id.trim_scene_descrip_text);
            mImg = itemView.findViewById(R.id.img_view);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position,List<TrimSceneBean> list);
        void onItemLongClick(View view , int position,List<TrimSceneBean> list);
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
}
