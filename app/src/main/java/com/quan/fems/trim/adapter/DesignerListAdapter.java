package com.quan.fems.trim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.quan.fems.trim.R;

import java.util.List;

public class DesignerListAdapter extends RecyclerView.Adapter<DesignerListAdapter.ViewHolder> {
    private List<String> list;
    private DesignerListAdapter.OnItemClickListener mOnItemClickListener;
    public DesignerListAdapter(List<String> list) {
        this.list = list;
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
        holder.mText.setText(list.get(position));
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
        TextView mText;
        ViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.name_view);
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
}
