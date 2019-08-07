package com.quan.fems.trim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.TrimSceneAdapter;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.bean.TrimSceneBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrimSceneActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;
    private RecyclerView mRecyclerView;
    private TrimSceneAdapter mAdapter;
    private List<TrimSceneBean> listData;
    private Intent intent=null;
    private String getParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_list);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        backView = findViewById(R.id.back_view);
        titleName = findViewById(R.id.title_name);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView=findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter=new TrimSceneAdapter(listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        titleName.setText("识尚装饰");
        intent=getIntent();
        getParam=intent.getStringExtra("styleName");
        loadRecycleData();
    }

    private void loadRecycleData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = mHttpListener;
        if (!getParam.equals("all")){
            prm.param.add("styleName",getParam);
        }
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

    private void initEvent() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrimSceneActivity.this.finish();
            }
        });
        mAdapter.setOnItemClickListener(new TrimSceneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<TrimSceneBean> list) {
                Intent intent=new Intent(TrimSceneActivity.this,TrimSceneDetailActivity.class);
                intent.putExtra("id",list.get(position).id);
                TrimSceneActivity.this.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, List<TrimSceneBean> list) {

            }
        });
    }
}
