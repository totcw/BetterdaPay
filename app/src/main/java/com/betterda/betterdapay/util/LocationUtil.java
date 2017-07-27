package com.betterda.betterdapay.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/7/27
 * 功能说明： 获取定位信息
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class LocationUtil {

    /**
     * 定位功能
     */
    private LocationClient mLocationClient = null; //定位的类
    private BDLocationListener myListener = new MyLocationListener();
    private MyBDLocationListener mMyBDLocationListener = null;

    public void start(Context context,MyBDLocationListener myBDLocationListener) {
        this.mMyBDLocationListener = myBDLocationListener;

        //声明LocationClient类
        mLocationClient = new LocationClient(context);
        //注册监听函数
        mLocationClient.registerLocationListener( myListener );

        initLocation(mLocationClient);

        mLocationClient.start();
    }


    /**
     * 设置定位的参数
     */
    public static void initLocation(LocationClient mLocationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
       // int span = 1000;
       // option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }



    /**
     * 定位的回调方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (mMyBDLocationListener != null) {
                mMyBDLocationListener.onReceive(location);
            }

            //停止定位
            if (mLocationClient != null) {

                mLocationClient.stop();
                mLocationClient = null;
                myListener = null;
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }


    public interface MyBDLocationListener{
        void onReceive(BDLocation location);
    }


}
