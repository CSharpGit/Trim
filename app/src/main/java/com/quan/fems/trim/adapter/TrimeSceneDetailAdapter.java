package com.quan.fems.trim.adapter;

import android.content.Context;
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
import com.quan.fems.trim.bean.HouseConfigBean;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.untils.ImageDisplayListener;
import java.util.List;

import static com.quan.fems.trim.untils.ScreenInfoUtils.getDensity;
import static com.quan.fems.trim.untils.ScreenInfoUtils.getScreenWidth;

public class TrimeSceneDetailAdapter extends RecyclerView.Adapter<TrimeSceneDetailAdapter.ViewHolder> {
    protected DisplayImageOptions options=null;
    protected ImageLoadingListener mImageLoadingListener=new ImageDisplayListener();
    private Context mContext;
    private List<HouseConfigBean> list;
    private OnItemClickListener mOnItemClickListener;
    public TrimeSceneDetailAdapter(Context mContext,List<HouseConfigBean> list) {
        this.mContext=mContext;
        this.list = list;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trim_scene_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cfgName.setText(list.get(position).cfgName);
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+list.get(position).imgurl,holder.cfgIma,options,mImageLoadingListener);
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemClick(v,position);
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
        TextView cfgName;
        ImageView cfgIma;
        ViewHolder(View itemView) {
            super(itemView);
            cfgName = itemView.findViewById(R.id.cfg_name_text);
            cfgIma = itemView.findViewById(R.id.cfg_img_view);
            ViewGroup.LayoutParams imgParam=cfgIma.getLayoutParams();
            imgParam.width=(int)(getScreenWidth(mContext)-30*getDensity(mContext));
            imgParam.height=(int)(imgParam.width/1.78);
            cfgIma.setLayoutParams(imgParam);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
}
