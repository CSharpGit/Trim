package com.quan.fems.trim.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.quan.fems.trim.R;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.server.Commons;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DesignerListAdapter extends RecyclerView.Adapter<DesignerListAdapter.ViewHolder> {
    private Context context;
    protected DisplayImageOptions options;
    protected ImageLoadingListener mImageLoadingListener=new ImageDisplayListener();
    private List<DesignerBean> list;
    private DesignerListAdapter.OnItemClickListener mOnItemClickListener;
    public DesignerListAdapter(Context context,List<DesignerBean> list) {
        this.list = list;
        this.context=context;
    }
    public void setOnItemClickListener(DesignerListAdapter.OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public DesignerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designer_list_item, parent, false);
        DesignerListAdapter.ViewHolder viewHolder = new DesignerListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DesignerListAdapter.ViewHolder holder, final int position) {
        holder.mName.setText(list.get(position).dName);
        holder.mPosit.setText(list.get(position).posit);
        holder.mGoodAt.setText(list.get(position).goodAt);
        holder.mExperi.setText(""+list.get(position).experi);
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+list.get(position).imgurl,holder.mIma,options,mImageLoadingListener);
        holder.callTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(list.get(position).tel);
            }
        });
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
        TextView mName,mPosit,mGoodAt,mExperi;
        ImageView mIma;
        LinearLayout callTel;
        ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name_view);
            mPosit = itemView.findViewById(R.id.posit_view);
            mGoodAt = itemView.findViewById(R.id.good_at_text);
            mExperi = itemView.findViewById(R.id.experi_text);
            mIma = itemView.findViewById(R.id.img_view);
            callTel = itemView.findViewById(R.id.call_tel);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position,List<DesignerBean> list);
        void onItemLongClick(View view , int position,List<DesignerBean> list);
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

    public void callPhone(String str) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        context.startActivity(intent);
    }
}
