package com.quan.fems.trim.frag;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quan.fems.trim.MainActivity;
import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.HomeIconAdapter;
import com.quan.fems.trim.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment{
    private View view = null;
    private RecyclerView mHomeIconRecyclerView;
    private List<String> listData;
    private HomeIconAdapter mHomeIconAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_home,container,false);
        initView();
        initData();
        return view;
    }
    private void initView(){
        mHomeIconRecyclerView=view.findViewById(R.id.recycler_view_icon);
        listData=new ArrayList<>();
        listData.add("图标1");
        listData.add("图标1");
        listData.add("图标1");
        listData.add("图标1");
        mHomeIconAdapter=new HomeIconAdapter(getActivity(),listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        mHomeIconRecyclerView.setLayoutManager(staggered);
        mHomeIconRecyclerView.setAdapter(mHomeIconAdapter);
    }
    private void initData(){
    }
}
