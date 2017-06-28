package com.betterda.betterdapay.activity;

import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.application.MyApplication;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.UtilMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/14
 * 功能说明： 引导界面
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_guide);


    }

    @Override
    public void init() {
        super.init();
        List<View> list = new ArrayList<>();

        ImageView imageView = new ImageView(this);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.mipmap.guide1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView2.setLayoutParams(params);
        imageView2.setImageResource(R.mipmap.guide2);

        ImageView imageView3 = new ImageView(this);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView3.setLayoutParams(params);
        imageView3.setImageResource(R.mipmap.guide3);

        list.add(imageView);
        list.add(imageView2);
        list.add(imageView3);

        mVpGuide.setAdapter(new MyAdapter(list));

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putBoolean(GuideActivity.this, Constants.Cache.GUIDE,true);
                UtilMethod.startIntent(GuideActivity.this,LoginActivity.class);
                GuideActivity.this.finish();
            }
        });


    }

    class MyAdapter extends PagerAdapter {
        private List<View> list;


        public MyAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            if (null != list) {
                return list.size();
            } else {
                return 0;
            }

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (list == null || list.size() == 0) {
                return null;
            }
            View view = list.get(position);
            if (null != view) {
                container.addView(view);
                return view;
            }
            return null;

        }
    }


}
