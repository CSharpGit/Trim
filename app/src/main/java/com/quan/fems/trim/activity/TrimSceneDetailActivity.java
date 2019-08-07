package com.quan.fems.trim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.TrimeSceneDetailAdapter;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.base.OnSingleClickListener;
import com.quan.fems.trim.bean.HouseConfigBean;
import com.quan.fems.trim.bean.TrimSceneBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrimSceneDetailActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName,toCalculator,toReserve;
    private TextView mHouseType,mStyleLable,mAear;
    private RecyclerView mRecyclerView;
    private TrimeSceneDetailAdapter mAdapter;
    private List<HouseConfigBean> listData;
    private Intent intent=null;
    private int getId=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trim_scene_detail);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        backView = findViewById(R.id.back_view);
        titleName = findViewById(R.id.title_name);
        toCalculator=findViewById(R.id.to_calculator);
        toReserve=findViewById(R.id.to_reserve);
        mHouseType=findViewById(R.id.house_type);
        mStyleLable=findViewById(R.id.trim_scene_style_text);
        mAear=findViewById(R.id.area_text);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView=findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        mAdapter=new TrimeSceneDetailAdapter(listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        titleName.setText("识尚装饰");
        intent=getIntent();
        getId=intent.getIntExtra("id",1);
        loadData();
    }

    private void loadData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = mHttpListener;
        prm.param.add("id",getId);
        prm.url = Commons.TRIMSCENEDETAIL;
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
                    JSONObject getJsn=dd.getJSONObject(0);
                    mHouseType.setText(getJsn.getString("houseType"));
                    mStyleLable.setText(getJsn.getString("style"));
                    mAear.setText(getJsn.getString("area"));
                    JSONArray cfgArr=getJsn.getJSONArray("config");
                    for (int i=0;i<cfgArr.length();i++){
                        JSONObject cfgList=cfgArr.getJSONObject(i);
                        HouseConfigBean hcb = new HouseConfigBean();
                        hcb.cfgName=cfgList.getString("name");
                        hcb.imgurl=cfgList.getString("img");
                        listData.add(hcb);
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
                TrimSceneDetailActivity.this.finish();
            }
        });
        toCalculator.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent=new Intent(TrimSceneDetailActivity.this,CalculatorActivity.class);
                TrimSceneDetailActivity.this.startActivity(intent);
            }
        });
        toReserve.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent=new Intent(TrimSceneDetailActivity.this,ReserveActivity.class);
                TrimSceneDetailActivity.this.startActivity(intent);
            }
        });
    }
}
