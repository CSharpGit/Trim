package com.quan.fems.trim;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quan.fems.trim.base.BaseActivity;
import com.quan.fems.trim.base.OnSingleClickListener;
import com.quan.fems.trim.frag.HomeFragment;
import com.quan.fems.trim.frag.ReserveFragment;
import com.quan.fems.trim.frag.SceneFragment;

public class MainActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName;
    private FrameLayout mFrameLayout;
    private FragmentTransaction mFragmentTransaction = null;
    private FragmentManager mFragmentManager = null;
    private HomeFragment mHomeFragment=null;
    private SceneFragment mSceneFragment=null;
    private ReserveFragment mReserveFragment=null;
    private int mTab=0;
    private ImageView menu_img_1,menu_img_2,menu_img_3;
    private TextView menu_text_1,menu_text_2,menu_text_3;
    private LinearLayout menu_1,menu_2,menu_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        backView=findViewById(R.id.back_view);
        titleName=findViewById(R.id.title_name);
        mFragmentManager = getSupportFragmentManager();
        mFrameLayout = findViewById(R.id.frame_view);
        mHomeFragment = new HomeFragment();
        mFragmentManager.beginTransaction().add(R.id.frame_view,mHomeFragment).commit();
        menu_img_1=findViewById(R.id.menu_img_1);
        menu_img_2=findViewById(R.id.menu_img_2);
        menu_img_3=findViewById(R.id.menu_img_3);
        menu_text_1=findViewById(R.id.menu_text_1);
        menu_text_2=findViewById(R.id.menu_text_2);
        menu_text_3=findViewById(R.id.menu_text_3);
        menu_1=findViewById(R.id.menu_1);
        menu_2=findViewById(R.id.menu_2);
        menu_3=findViewById(R.id.menu_3);
    }

    private void initData() {
        backView.setVisibility(View.GONE);
        titleName.setText("识尚装饰");
    }
    private void initEvent() {
        menu_1.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                tabActivity(1);
            }
        });
        menu_2.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                tabActivity(2);
            }
        });
        menu_3.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                tabActivity(3);
            }
        });
    }

    private void tabActivity(int ndx){
        mTab = ndx;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        clean();
        switch (ndx){
            case 1:
                menu_img_1.setImageResource(R.drawable.home_green);
                menu_text_1.setTextColor(Color.parseColor("#33bd82"));
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    mFragmentTransaction.add(R.id.frame_view,mHomeFragment);
                }else {
                    mFragmentTransaction.show(mHomeFragment);
                }
                break;
            case 2:
                menu_img_2.setImageResource(R.drawable.img_green);
                menu_text_2.setTextColor(Color.parseColor("#33bd82"));
                if (mSceneFragment == null){
                    mSceneFragment = new SceneFragment();
                    mFragmentTransaction.add(R.id.frame_view,mSceneFragment);
                }else {
                    mFragmentTransaction.show(mSceneFragment);
                }
                break;
            case 3:
                menu_img_3.setImageResource(R.drawable.reserve_green);
                menu_text_3.setTextColor(Color.parseColor("#33bd82"));
                if (mReserveFragment == null){
                    mReserveFragment = new ReserveFragment();
                    mFragmentTransaction.add(R.id.frame_view,mReserveFragment);
                }else {
                    mFragmentTransaction.show(mReserveFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }

    private void clean(){
        menu_img_1.setImageResource(R.drawable.home_gray);
        menu_img_2.setImageResource(R.drawable.img_gray);
        menu_img_3.setImageResource(R.drawable.reserve_gray);
        menu_text_1.setTextColor(Color.parseColor("#8c8c8c"));
        menu_text_2.setTextColor(Color.parseColor("#8c8c8c"));
        menu_text_3.setTextColor(Color.parseColor("#8c8c8c"));
        if(mHomeFragment!=null){
            mFragmentTransaction.hide(mHomeFragment);
        }
        if(mSceneFragment!=null){
            mFragmentTransaction.hide(mSceneFragment);
        }
        if(mReserveFragment!=null){
            mFragmentTransaction.hide(mReserveFragment);
        }
    }

}
