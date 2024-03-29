package com.quan.fems.trim.frag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quan.fems.trim.R;
import com.quan.fems.trim.activity.CalculatorActivity;
import com.quan.fems.trim.activity.DesignerDetailActivity;
import com.quan.fems.trim.activity.DesignerListActivity;
import com.quan.fems.trim.activity.ReserveActivity;
import com.quan.fems.trim.activity.TrimSceneActivity;
import com.quan.fems.trim.adapter.HomeDesignerAdapter;
import com.quan.fems.trim.adapter.HomeIconAdapter;
import com.quan.fems.trim.adapter.HomeTrimSceneAdapter;
import com.quan.fems.trim.adapter.SlideAdapter;
import com.quan.fems.trim.base.BaseFragment;
import com.quan.fems.trim.base.OnSingleClickListener;
import com.quan.fems.trim.bean.DesignerBean;
import com.quan.fems.trim.bean.HomeIconBean;
import com.quan.fems.trim.bean.HomeTrimSceneBean;
import com.quan.fems.trim.bean.SlideBean;
import com.quan.fems.trim.server.AsyncHttpCient;
import com.quan.fems.trim.server.Commons;
import com.quan.fems.trim.server.HttpListener;
import com.quan.fems.trim.server.HttpParam;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.quan.fems.trim.untils.ScreenInfoUtils.*;

public class HomeFragment extends BaseFragment{
    private View view = null;
    private RefreshLayout refreshLayout;
    private ConstraintLayout toDesignerList,toTrimScene;
    private RecyclerView mHomeIconRecyclerView,mHomeDesignerRecyclerView,mHomeTrimSceneRecyclerView;
    private HomeIconAdapter mHomeIconAdapter;
    private HomeDesignerAdapter mHomeDesignerAdapter;
    private HomeTrimSceneAdapter mHomeTrimSceneAdapter;
    private ViewPager mViewPager;
    private ViewGroup slide;
    private ImageView[] slide_list;
    private int currentItem=0;
    private ScheduledExecutorService scheduledExecutorService;

    private ArrayList<SlideBean> bannerListBean=new ArrayList<>();
    private List<HomeIconBean> iconListBean;
    private List<DesignerBean> designerListBean;
    private List<HomeTrimSceneBean> sceneListBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView(){
        refreshLayout=view.findViewById(R.id.refreshLayout);
        mViewPager = view.findViewById(R.id.viewPager);
        slide = view.findViewById(R.id.banner_slide);
        toDesignerList=view.findViewById(R.id.to_designer_list);
        toTrimScene=view.findViewById(R.id.to_trim_scene);
        initBannerView();
        initRefreshView();
        initIconRecyclerView();
        initDesignerRecyclerView();
        initTrimSceneRecyclerView();
    }

    private void initData(){
        loadBannerData();
        loadIconData();
        loadDesignerData();
        loadTrimSceneData();
    }
    private void initEvent() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }
        });
        mHomeIconAdapter.setOnItemClickListener(new HomeIconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent calIntent =new Intent(getActivity(),CalculatorActivity.class);
                        getActivity().startActivity(calIntent);
                        break;
                    case 1:
                        callPhone("18856235864");
                        break;
                    case 2:
                        Intent resIntent =new Intent(getActivity(),ReserveActivity.class);
                        getActivity().startActivity(resIntent);
                        break;
                    case 3:
                        Intent designerIntent =new Intent(getActivity(),DesignerListActivity.class);
                        getActivity().startActivity(designerIntent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mHomeDesignerAdapter.setOnItemClickListener(new HomeDesignerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,List<DesignerBean> list) {
                Intent intent =new Intent(getActivity(),DesignerDetailActivity.class);
                intent.putExtra("did",list.get(position).id);
                intent.putExtra("tel",list.get(position).tel);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position,List<DesignerBean> list) {

            }
        });
        mHomeTrimSceneAdapter.setOnItemClickListener(new HomeTrimSceneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,List<HomeTrimSceneBean> list) {
                Intent intent =new Intent(getActivity(),TrimSceneActivity.class);
                intent.putExtra("styleName",list.get(position).styleLabel);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position,List<HomeTrimSceneBean> list) {

            }
        });
        toDesignerList.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent=new Intent(getActivity(),DesignerListActivity.class);
                getActivity().startActivity(intent);
            }
        });
        toTrimScene.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent=new Intent(getActivity(),TrimSceneActivity.class);
                intent.putExtra("styleName","all");
                getActivity().startActivity(intent);
            }
        });
    }

    private void initBannerView() {
        ViewGroup.LayoutParams bannerParm = mViewPager.getLayoutParams();
        bannerParm.width = getScreenWidth(getActivity());
        bannerParm.height = bannerParm.width / 2;
        mViewPager.setLayoutParams(bannerParm);
    }

    private void initRefreshView() {
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setPrimaryColorsId(R.color.appTheme, android.R.color.white);
    }
    private void initIconRecyclerView() {
        mHomeIconRecyclerView=view.findViewById(R.id.recycler_view_icon);
        iconListBean =new ArrayList<>();
        mHomeIconAdapter=new HomeIconAdapter(iconListBean);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        mHomeIconRecyclerView.setLayoutManager(staggered);
        mHomeIconRecyclerView.setAdapter(mHomeIconAdapter);
    }
    private void initDesignerRecyclerView() {
        mHomeDesignerRecyclerView=view.findViewById(R.id.recycler_view_designer);
        designerListBean=new ArrayList<>();
        mHomeDesignerAdapter=new HomeDesignerAdapter(designerListBean);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mHomeDesignerRecyclerView.setLayoutManager(staggered);
        mHomeDesignerRecyclerView.setAdapter(mHomeDesignerAdapter);
    }
    private void initTrimSceneRecyclerView() {
        mHomeTrimSceneRecyclerView=view.findViewById(R.id.recycler_view_trim_scene);
        sceneListBean=new ArrayList<>();
        mHomeTrimSceneAdapter=new HomeTrimSceneAdapter(sceneListBean);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        mHomeTrimSceneRecyclerView.setLayoutManager(staggered);
        mHomeTrimSceneRecyclerView.setAdapter(mHomeTrimSceneAdapter);
    }

    private void loadIconData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = iconHttpListener;
        prm.url = Commons.ICON;
        hndl.execute(prm);
    }
    private void loadDesignerData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = designerHttpListener;
        prm.url = Commons.HOMEDESIGNER;
        hndl.execute(prm);
    }
    private void loadTrimSceneData() {
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = trimSceneHttpListener;
        prm.url = Commons.HOMETRIMSCENE;
        hndl.execute(prm);
    }

    private HttpListener iconHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                iconListBean.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        HomeIconBean hib = new HomeIconBean();
                        hib.id=jsnList.getInt("_id");
                        hib.imgurl=jsnList.getString("img");
                        hib.titleName=jsnList.getString("titleName");
                        iconListBean.add(hib);
                    }
                    mHomeIconAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private HttpListener designerHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                designerListBean.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        DesignerBean hdb = new DesignerBean();
                        hdb.id=jsnList.getInt("_id");
                        hdb.imgurl=jsnList.getString("img");
                        hdb.dName=jsnList.getString("name");
                        hdb.posit=jsnList.getString("posit");
                        hdb.tel=jsnList.getString("tel");
                        designerListBean.add(hdb);
                    }
                    mHomeDesignerAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private HttpListener trimSceneHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                sceneListBean.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        HomeTrimSceneBean htsb = new HomeTrimSceneBean();
                        htsb.id=jsnList.getInt("_id");
                        htsb.imgurl=jsnList.getString("img");
                        htsb.styleLabel=jsnList.getString("style");
                        htsb.styleCount=dd.length();
                        sceneListBean.add(htsb);
                    }
                    mHomeTrimSceneAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void callPhone(String str) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        getActivity().startActivity(intent);
    }

    private void loadBannerData(){
        AsyncHttpCient hndl = new AsyncHttpCient();
        HttpParam prm = new HttpParam();
        prm.httpListener = bannerHttpListener;
        prm.url = Commons.BANNER;
        hndl.execute(prm);
    }
    private HttpListener bannerHttpListener = new HttpListener() {
        @Override
        public void onPostData(String data) {
            try {
                JSONObject jsn = new JSONObject(data);
                bannerListBean.clear();
                if(jsn.getInt("errcode")==0){
                    JSONArray dd = jsn.getJSONArray("data");
                    deleteAllView(slide);
                    slide_list = new ImageView[dd.length()];
                    for (int i=0;i<dd.length();i++){
                        JSONObject jsnList=dd.getJSONObject(i);
                        SlideBean sb = new SlideBean();
                        sb.id=jsnList.getInt("_id");
                        sb.imgurl=jsnList.getString("img");
                        bannerListBean.add(sb);
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
                    SlideAdapter sdp = new SlideAdapter(getActivity(),bannerListBean);
                    mViewPager.setAdapter(sdp);
                    mViewPager.addOnPageChangeListener(new MyPageChangeListener());
                    sdp.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void deleteAllView(ViewGroup vg){
        int size = vg.getChildCount();
        if (size>0){
            for( int i = 0; i < size; i++){
                vg.removeViewAt(i);
            }
        }
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
