package com.quan.fems.trim.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.adapter.HomeDesignerAdapter;
import com.quan.fems.trim.adapter.HomeIconAdapter;
import com.quan.fems.trim.adapter.HomeTrimSceneAdapter;
import com.quan.fems.trim.adapter.SlideAdapter;
import com.quan.fems.trim.base.BaseFragment;
import com.quan.fems.trim.bean.SlideBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends BaseFragment{
    private View view = null;
    private RecyclerView mHomeIconRecyclerView,mHomeDesignerRecyclerView,mHomeTrimSceneRecyclerView;
    private List<String> iListData,dListData,tListData;
    private HomeIconAdapter mHomeIconAdapter;
    private HomeDesignerAdapter mHomeDesignerAdapter;
    private HomeTrimSceneAdapter mHomeTrimSceneAdapter;
    private ViewPager mViewPager;
    private ViewGroup slide;
    private ImageView[] slide_list;
    private int currentItem=0;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initData();
        return view;
    }
    private void initView(){
        mViewPager = view.findViewById(R.id.viewPager);
        slide = view.findViewById(R.id.banner_slide);
        initIconRecyclerView();
        initDesignerRecyclerView();
        initTrimSceneRecyclerView();
    }

    private void initData(){
        initBannerData();
    }

    private void initBannerData() {
        ArrayList<SlideBean> lsts = new ArrayList<SlideBean>();
        slide_list = new ImageView[4];
        for (int i=0;i<4;i++){
            SlideBean sb = new SlideBean();
            sb.id=1;
            sb.imgurl="banner.png";
            lsts.add(sb);
            ImageView iView = new ImageView(getActivity());
            //点大小
            iView.setLayoutParams(new ViewGroup.LayoutParams(20,20));
            slide_list[i] = iView;
            if (i == 0) {
                slide_list[i].setBackgroundResource(R.drawable.slide_2);
            }else {
                slide_list[i].setBackgroundResource(R.drawable.slide_1);
            }
            slide.addView(slide_list[i]);
        }
        SlideAdapter sdp = new SlideAdapter(getActivity(),lsts);
        mViewPager.setAdapter(sdp);
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    private void initIconRecyclerView() {
        mHomeIconRecyclerView=view.findViewById(R.id.recycler_view_icon);
        iListData=new ArrayList<>();
        iListData.add("图标1");
        iListData.add("图标1");
        iListData.add("图标1");
        iListData.add("图标1");
        mHomeIconAdapter=new HomeIconAdapter(getActivity(),iListData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        mHomeIconRecyclerView.setLayoutManager(staggered);
        mHomeIconRecyclerView.setAdapter(mHomeIconAdapter);
    }

    private void initDesignerRecyclerView() {
        mHomeDesignerRecyclerView=view.findViewById(R.id.recycler_view_designer);
        dListData=new ArrayList<>();
        dListData.add("李冰");
        dListData.add("李冰");
        dListData.add("李冰");
        dListData.add("李冰");
        mHomeDesignerAdapter=new HomeDesignerAdapter(getActivity(),dListData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mHomeDesignerRecyclerView.setLayoutManager(staggered);
        mHomeDesignerRecyclerView.setAdapter(mHomeDesignerAdapter);
    }

    private void initTrimSceneRecyclerView() {
        mHomeTrimSceneRecyclerView=view.findViewById(R.id.recycler_view_trim_scene);
        tListData=new ArrayList<>();
        tListData.add("欧式简约");
        tListData.add("欧式简约");
        tListData.add("欧式简约");
        tListData.add("欧式简约");
        mHomeTrimSceneAdapter=new HomeTrimSceneAdapter(getActivity(),tListData);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mHomeTrimSceneRecyclerView.setLayoutManager(staggered);
        mHomeTrimSceneRecyclerView.setAdapter(mHomeTrimSceneAdapter);
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageSelected(int position) {
            for (int i = 0; i < slide_list.length; i++) {
                if (position == i) {
                    slide_list[i].setBackgroundResource(R.drawable.slide_2);
                }else {
                    slide_list[i].setBackgroundResource(R.drawable.slide_1);
                }
            }
        }
        public void onPageScrollStateChanged(int arg0) {

        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /*
     * 切换当前显示的图片
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    /*
     * 换行切换任务
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % slide_list.length;
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    /*
     * 以下为处理滑动图片
     */
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(),5,4, TimeUnit.SECONDS);
        super.onStart();
    }
    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }
}
