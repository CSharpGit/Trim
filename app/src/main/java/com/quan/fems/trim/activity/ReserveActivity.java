package com.quan.fems.trim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;

public class ReserveActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        backView = findViewById(R.id.back_view);
        titleName = findViewById(R.id.title_name);
    }

    private void initData() {
        titleName.setText("识尚装饰");
    }

    private void initEvent() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReserveActivity.this.finish();
            }
        });
    }
}
