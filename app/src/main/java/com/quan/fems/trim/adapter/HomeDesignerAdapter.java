package com.quan.fems.trim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quan.fems.trim.R;

import java.util.List;

public class HomeDesignerAdapter extends  RecyclerView.Adapter<HomeDesignerAdapter.MyViewHolder> {
    private List<String> listData;
    private Context mContext;
    private LayoutInflater inflater;
    public HomeDesignerAdapter(Context context, List<String> data){
        this. mContext=context;
        this.listData=data;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nameView.setText( listData.get(position));
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.designer_item,parent,false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        public MyViewHolder(View view) {
            super(view);
            nameView=view.findViewById(R.id.name_view);
        }

    }
}
