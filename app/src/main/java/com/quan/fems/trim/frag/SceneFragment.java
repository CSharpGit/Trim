package com.quan.fems.trim.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quan.fems.trim.R;
import com.quan.fems.trim.activity.TrimSceneDetailActivity;
import com.quan.fems.trim.adapter.TrimSceneAdapter;
import com.quan.fems.trim.base.BaseFragment;
import com.quan.fems.trim.bean.TrimSceneBean;

import java.util.ArrayList;
import java.util.List;

public class SceneFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private TrimSceneAdapter mAdapter;
    private List<TrimSceneBean> listData;
    private View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_scene,container,false);
        initView();
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new TrimSceneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),TrimSceneDetailActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initView(){
        initRecyclerView();
    }
    private void initData(){
//        for (int i=0;i<20;i++){
//            listData.get(i).styleName="欧式简约";
//        }
        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        mRecyclerView=view.findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter=new TrimSceneAdapter(listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }
}
