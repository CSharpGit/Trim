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
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

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
            public void onItemClick(View view, int position,List<TrimSceneBean> list) {
                Intent intent=new Intent(getActivity(),TrimSceneDetailActivity.class);
                intent.putExtra("id",list.get(position).id);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position,List<TrimSceneBean> list) {

            }
        });
    }

    private void initView(){
        initRecyclerView();
    }
    private void initData(){
        loadRecycleData();
        mAdapter.notifyDataSetChanged();
    }

    private void loadRecycleData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = mHttpListener;
        prm.url = Commons.TRIMSCENE;
        hndl.execute(prm);
    }

    private HttpListener mHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                listData.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        TrimSceneBean tsb = new TrimSceneBean();
                        tsb.id=jsnList.getInt("_id");
                        tsb.imgurl=jsnList.getString("img");
                        tsb.styleName=jsnList.getString("style");
                        tsb.descrip=jsnList.getString("descri");
                        listData.add(tsb);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void initRecyclerView() {
        mRecyclerView=view.findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter=new TrimSceneAdapter(listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }
}
