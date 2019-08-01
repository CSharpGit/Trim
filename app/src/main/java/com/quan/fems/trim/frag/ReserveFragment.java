package com.quan.fems.trim.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseFragment;

public class ReserveFragment extends BaseFragment{
    private View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_reserve,container,false);
        initView();
        initData();
        return view;
    }
    private void initView(){
    }
    private void initData(){
    }
}
