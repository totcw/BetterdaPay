package com.betterda.betterdapay.activity;

import android.widget.Toast;

import com.betterda.betterdapay.R;
import com.betterda.mylibrary.cityView.ChooseCityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择城市
 * Created by Administrator on 2016/8/15.
 */
public class ChooseCityActivity extends BaseActivity {

    @BindView(R.id.ccv_city)
    ChooseCityView ccvCity;
    private List<String> list;

    @Override
    public void initView() {
        setContentView(R.layout.activity_choosecity);
    }

    @Override
    public void init() {
        super.init();
        list = new ArrayList<>();
        list.add("广州");
        list.add("北京");
        list.add("上海");
        list.add("深圳");

        //根据定位信息来设置定位城市
        ccvCity.setLoacitonCity("北京");
        //设置热门城市数据
        ccvCity.setHotCityList(list);
        //设置热门城市的点击事件
        ccvCity.setOnCityItemClickListner(new ChooseCityView.onCityItemClickListner() {
            @Override
            public void click(int position) {
                Toast.makeText(getApplicationContext(), list.get(position), 0).show();
            }
        });

        //设置主要城市的点击事件
        ccvCity.setOnSortItemClickListner(new ChooseCityView.onSortItemClickListner() {
            @Override
            public void click(String city) {
                Toast.makeText(getApplicationContext(),city,0).show();
            }
        });
    }
}
