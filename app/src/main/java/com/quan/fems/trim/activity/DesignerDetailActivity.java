package com.quan.fems.trim.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.base.OnSingleClickListener;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;

import org.json.JSONArray;
import org.json.JSONObject;

public class DesignerDetailActivity extends BaseActivity {
    private ImageView backView,mImag;
    private TextView titleName,mName,mPosit,mConcept,mIntro;
    private LinearLayout callTel,toReserve;
    private Intent intent=null;
    private int d_id=1;
    private String dTel;

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
                    ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+jsnList.getString("img"),mImag,options,mImageLoadingListener);
                    mName.setText(jsnList.getString("name"));
                    mPosit.setText(jsnList.getString("posit"));
                    mConcept.setText(jsnList.getString("concept"));
                    mIntro.setText("\u3000\u3000"+jsnList.getString("intro"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void initView() {
        backView = findViewById(R.id.back_view);
        titleName = findViewById(R.id.title_name);
        mImag = findViewById(R.id.img_view);
        mName = findViewById(R.id.name_text);
        mPosit = findViewById(R.id.posit_text);
        mConcept = findViewById(R.id.concept_text);
        mIntro = findViewById(R.id.intro_text);
        callTel = findViewById(R.id.call_tel);
        toReserve = findViewById(R.id.to_reserve);
    }

    public void callPhone(String str) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        DesignerDetailActivity.this.startActivity(intent);
    }

    private void initData() {
        intent=getIntent();
        d_id=intent.getIntExtra("did",1);
        dTel=intent.getStringExtra("tel");
        titleName.setText("识尚装饰");
        loadData();
    }

    private void initEvent() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DesignerDetailActivity.this.finish();
            }
        });
        callTel.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                callPhone(dTel);
            }
        });
        toReserve.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent = new Intent(DesignerDetailActivity.this,ReserveActivity.class);
                DesignerDetailActivity.this.startActivity(intent);
            }
        });
    }
}
