package com.quan.fems.trim.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;

public class ReserveActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName,messSub;
    private EditText nameInput,phoneNumberInput;

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
        messSub = findViewById(R.id.mess_sub);
        nameInput = findViewById(R.id.input_name);
        phoneNumberInput = findViewById(R.id.input_phone_number);
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
        messSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judInput()){
                    Toast.makeText(ReserveActivity.this, "您的预约申请已提交，请保持电话畅通，我们将在2小时内与您联系！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean judInput(){
        if (TextUtils.isEmpty(nameInput.getText().toString().trim())) {
            Toast.makeText(ReserveActivity.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            nameInput.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phoneNumberInput.getText().toString().trim())) {
            Toast.makeText(ReserveActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
            phoneNumberInput.requestFocus();
            return false;
        } else if (phoneNumberInput.getText().toString().trim().length() != 11) {
            Toast.makeText(ReserveActivity.this, "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            phoneNumberInput.requestFocus();
            return false;
        } else {
            String phone_number = phoneNumberInput.getText().toString().trim();
            String num = "[1][358]\\d{9}";
            if (phone_number.matches(num)) return true;
            else {
                Toast.makeText(ReserveActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
