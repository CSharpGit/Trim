package com.quan.fems.trim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;

public class CalculatorActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName, chooseHouseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        backView = findViewById(R.id.back_view);
        titleName = findViewById(R.id.title_name);
        chooseHouseType = findViewById(R.id.choose_house_type);
    }

    private void initData() {
        titleName.setText("识尚装饰");
    }

    private void initEvent() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorActivity.this.finish();
            }
        });
        chooseHouseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
