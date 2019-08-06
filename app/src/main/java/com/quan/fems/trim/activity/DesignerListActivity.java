package com.quan.fems.trim.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.DesignerListAdapter;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DesignerListActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;
    private RecyclerView mRecyclerView;
    private DesignerListAdapter mAdapter;
    private List<DesignerBean> designerListBean;

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
        designerListBean=new ArrayList<>();
        mAdapter=new DesignerListAdapter(DesignerListActivity.this,designerListBean);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        titleName.setText("识尚装饰");
        loadRecycleData();
    }

    private void loadRecycleData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = mHttpListener;
        prm.url = Commons.DESIGNERLIST;
        hndl.execute(prm);
    }

    private HttpListener mHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                designerListBean.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        DesignerBean hdb = new DesignerBean();
                        hdb.id=jsnList.getInt("_id");
                        hdb.imgurl=jsnList.getString("img");
                        hdb.dName=jsnList.getString("name");
                        hdb.posit=jsnList.getString("posit");
                        hdb.goodAt=jsnList.getString("goodat");
                        hdb.experi=jsnList.getInt("experi");
                        hdb.tel=jsnList.getString("tel");
                        designerListBean.add(hdb);
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
                DesignerListActivity.this.finish();
            }
        });
        mAdapter.setOnItemClickListener(new DesignerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,List<DesignerBean> list) {
                Intent intent=new Intent(DesignerListActivity.this,DesignerDetailActivity.class);
                intent.putExtra("id",list.get(position).id);
                DesignerListActivity.this.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position,List<DesignerBean> list) {
            }
        });
    }
}
