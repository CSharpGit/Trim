package com.quan.fems.trim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.DesignerListAdapter;
import com.quan.fems.trim.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DesignerListActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;
    private RecyclerView mRecyclerView;
    private DesignerListAdapter mAdapter;
    private List<String> listData;

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
        listData.add("李冰");
        listData.add("李冰");
        listData.add("李冰");
        listData.add("李冰");
        mAdapter=new DesignerListAdapter(listData);
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
                DesignerListActivity.this.finish();
            }
        });
        mAdapter.setOnItemClickListener(new DesignerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(DesignerListActivity.this,DesignerDetailActivity.class);
                DesignerListActivity.this.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
