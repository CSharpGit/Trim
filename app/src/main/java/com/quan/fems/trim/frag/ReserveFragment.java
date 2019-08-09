package com.quan.fems.trim.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseFragment;
import com.quan.fems.trim.server.Commons;

public class ReserveFragment extends BaseFragment{
    private View view = null;
    private TextView messSub;
    private EditText nameInput,phoneNumberInput;
    private ImageView mImgView;
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
        mImgView = view.findViewById(R.id.img_view);
        messSub=view.findViewById(R.id.mess_sub);
        nameInput = view.findViewById(R.id.input_name);
        phoneNumberInput = view.findViewById(R.id.input_phone_number);
    }
    private void initData(){
        ImageLoader.getInstance().displayImage(Commons.WEB_URL+Commons.IMG_DIR+"banner_3.jpg",mImgView,options,mImageLoadingListener);
    }

    private void initEvent() {
        messSub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (judInput()){
                    Toast.makeText(getActivity(), "您的预约申请已提交，请保持电话畅通，我们将在2小时内与您联系！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean judInput(){
        if (TextUtils.isEmpty(nameInput.getText().toString().trim())) {
            Toast.makeText(getActivity(), "请输入您的姓名", Toast.LENGTH_SHORT).show();
            nameInput.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phoneNumberInput.getText().toString().trim())) {
            Toast.makeText(getActivity(), "请输入您的电话号码", Toast.LENGTH_LONG).show();
            phoneNumberInput.requestFocus();
            return false;
        } else if (phoneNumberInput.getText().toString().trim().length() != 11) {
            Toast.makeText(getActivity(), "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            phoneNumberInput.requestFocus();
            return false;
        } else {
            String phone_number = phoneNumberInput.getText().toString().trim();
            String num = "[1][358]\\d{9}";
            if (phone_number.matches(num)) return true;
            else {
                Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
