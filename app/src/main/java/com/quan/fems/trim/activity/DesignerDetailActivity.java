package com.quan.fems.trim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

public class DesignerDetailActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;
    private Intent intent=getIntent();
    int d_id = intent.getIntExtra("id", 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_detail);
        initView();
        initData();
        initEvent();
    }

    private void loadData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = mHttpListener;
        prm.url = Commons.DESIGNERDTL;
        prm.param.add("id",d_id);
        hndl.execute(prm);
    }

    private HttpListener mHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    JSONObject jsnList=dd.getJSONObject(0);
                    System.out.println(jsnList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

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
                DesignerDetailActivity.this.finish();
            }
        });
    }
}
