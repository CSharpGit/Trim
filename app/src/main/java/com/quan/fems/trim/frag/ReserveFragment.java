package com.quan.fems.trim.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quan.fems.trim.PickerViewDemo;
import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseFragment;

public class ReserveFragment extends BaseFragment{
    private View view = null;
    private TextView messSub;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_reserve,container,false);
        initView();
        initData();
        initEvent();
        return view;
    }
    private void initView(){
        messSub=view.findViewById(R.id.mess_sub);
    }
    private void initData(){
    }

    private void initEvent() {
        messSub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PickerViewDemo.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
