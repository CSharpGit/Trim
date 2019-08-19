package com.quan.fems.trim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quan.fems.trim.R;
import com.quan.fems.trim.bean.HomeTrimSceneBean;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.untils.ImageDisplayListener;

import java.util.List;

public class HomeTrimSceneAdapter extends RecyclerView.Adapter<HomeTrimSceneAdapter.ViewHolder> {
    private DisplayImageOptions options=null;
    private ImageLoadingListener mImageLoadingListener=new ImageDisplayListener();
    private List<HomeTrimSceneBean> list;
    private OnItemClickListener mOnItemClickListener;
    public HomeTrimSceneAdapter(List<HomeTrimSceneBean> list) {
        this.list = list;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_trim_scene_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTrimScene.setText(list.get(position).styleLabel);
        holder.mTrimSceneCount.setText(""+list.get(position).styleCount);
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+list.get(position).imgurl,holder.mIma,options,mImageLoadingListener);
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
        TextView mTrimScene,mTrimSceneCount;
        ImageView mIma;
        ViewHolder(View itemView) {
            super(itemView);
            mTrimScene = itemView.findViewById(R.id.trim_scene_text);
            mTrimSceneCount = itemView.findViewById(R.id.trim_scene_style_text);
            mIma = itemView.findViewById(R.id.img_view);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position,List<HomeTrimSceneBean> list);
        void onItemLongClick(View view , int position,List<HomeTrimSceneBean> list);
    }
}
