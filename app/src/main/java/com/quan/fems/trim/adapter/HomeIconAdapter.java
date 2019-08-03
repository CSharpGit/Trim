package com.quan.fems.trim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quan.fems.trim.R;

import java.util.List;

public class HomeIconAdapter extends RecyclerView.Adapter<HomeIconAdapter.ViewHolder> {
    private List<String> list;
    private OnItemClickLitener mOnItemClickLitener;
    public HomeIconAdapter(List<String> list) {
        this.list = list;
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_button, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mText.setText(list.get(position));
        if(mOnItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemClick(v,position);
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
        TextView mText;
        ViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.name_view);
        }
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
}
