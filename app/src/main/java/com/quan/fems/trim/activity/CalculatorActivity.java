package com.quan.fems.trim.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.quan.fems.trim.GetJsonDataUtil;
import com.quan.fems.trim.JsonBean;
import com.quan.fems.trim.PickerViewDemo;
import com.quan.fems.trim.R;
import com.quan.fems.trim.base.BaseActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends BaseActivity {
    private ImageView backView;
    private TextView titleName, chooseCity, chooseHouseType;

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
        chooseCity = findViewById(R.id.choose_city);
        chooseHouseType = findViewById(R.id.choose_house_type);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initData() {
        titleName.setText("识尚装饰");
        loadNoLinkData();
        initNoLinkOptionsPicker();
    }

    private void initEvent() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorActivity.this.finish();
            }
        });
        chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(CalculatorActivity.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chooseHouseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvNoLinkOptions.show();
            }
        });
    }

    /********************************************************@ 省市3联动案例 start ****************************************************/

    //省市3联动案例
    private List<JsonBean> provinceItems = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityItems = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> districtItems = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        //Toast.makeText(CalculatorActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(CalculatorActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    Toast.makeText(CalculatorActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvtOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = provinceItems.size() > 0 ?
                        provinceItems.get(options1).getPickerViewText() : "";
                String opt2tx = cityItems.size() > 0
                        && cityItems.get(options1).size() > 0 ?
                        cityItems.get(options1).get(options2) : "";
                String opt3tx = cityItems.size() > 0
                        && districtItems.get(options1).size() > 0
                        && districtItems.get(options1).get(options2).size() > 0 ?
                        districtItems.get(options1).get(options2).get(options3) : "";
                String tx = opt1tx + opt2tx + opt3tx;
                chooseCity.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.GRAY)
                .setCancelColor(Color.GREEN)
                .setSubmitColor(Color.GREEN)
                .setSelectOptions(0,0,0)
                .isRestoreItem(true)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvtOptions.setPicker(provinceItems, cityItems, districtItems);//三级选择器
        pvtOptions.show();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provinceItems = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            cityItems.add(cityList);

            /**
             * 添加地区数据
             */
            districtItems.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
    /********************************************************@ 省市3联动案例 end ****************************************************/

    /********************************************************@ 不联动多级选项 start ****************************************************/

    //省市联动、多级选项案例
    private OptionsPickerView pvNoLinkOptions;
    private ArrayList<String> bedRoom = new ArrayList<>();
    private ArrayList<String> livingRoom = new ArrayList<>();
    private ArrayList<String> kikRoom = new ArrayList<>();
    /**
     * @功能：不联动多级选项
     * @必须函数：loadNoLinkData(),必须在初始化initOptionPicker()之前，先初始化数据
     * @gradlep依赖：implementation 'com.contrarywind:Android-PickerView:4.1.8'
     */
    private void initNoLinkOptionsPicker() {
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3,View v) {
                String str = bedRoom.get(options1)+" "+livingRoom.get(options2)+ " " + kikRoom.get(options3);
                chooseHouseType.setText(str);
                //Toast.makeText(CalculatorActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                //Toast.makeText(CalculatorActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        })
                .setSelectOptions(0, 0, 0)
                .build();
        pvNoLinkOptions.setNPicker(bedRoom, livingRoom, kikRoom);
    }

    private void loadNoLinkData() {
        bedRoom.add("1室");
        bedRoom.add("2室");
        bedRoom.add("3室");
        bedRoom.add("4室");

        livingRoom.add("1厅");
        livingRoom.add("2厅");
        livingRoom.add("3厅");

        kikRoom.add("1厨");
        kikRoom.add("2厨");
        kikRoom.add("3厨");
    }

    /********************************************************@ 不联动多级选项 end ****************************************************/
}
