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

import java.util.ArrayList;
import java.util.List;

public class TrimSceneDetailActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName,toCalculator,toReserve;
    private RecyclerView mRecyclerView;
    private TrimeSceneDetailAdapter mAdapter;
    private List<String> listData;

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
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView=findViewById(R.id.recycler_view);
        listData=new ArrayList<>();
        listData.add("客厅");
        listData.add("厨房");
        listData.add("书房");
        listData.add("主卧");
        mAdapter=new TrimeSceneDetailAdapter(listData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        titleName.setText("识尚装饰");
    }

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
