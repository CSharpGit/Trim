package com.quan.fems.trim.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SceneFragment extends BaseFragment{
    private RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private TrimSceneAdapter mAdapter;
    private ArrayList<TrimSceneBean> listData;
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }
        });
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
        refreshLayout=view.findViewById(R.id.refreshLayout);
        initRefreshView();
        initRecyclerView(2);
    }
    private void initData(){
        loadRecycleData();
        mAdapter.notifyDataSetChanged();
    }

    private void initRefreshView() {
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setPrimaryColorsId(R.color.appTheme, android.R.color.white);
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

    private void initRecyclerView(int colCount) {
        mRecyclerView=view.findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter=new TrimSceneAdapter(getActivity(),listData,colCount);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(colCount,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }
}
