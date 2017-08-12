package com.betterda.betterdapay.data;

import android.text.TextUtils;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 级别数据
 * Created by Administrator on 2016/8/18.
 */
public class RateData {
    public  static Map<String,Integer> rateMap;
    public  static Map<String,Integer> rateMap2;

    public final static String UP_YUANGONG = "1";
    public final static String UP_DIANZHANG = "2";
    public final static String UP_JINGLI= "3";
    public final static String UP_ZONGJINGLI = "4";
    public final static String UP_BOSS = "5";

    public final static String UP_YUANGONG_NAME = "员工";
    public final static String UP_DIANZHANG_NAME = "店长";
    public final static String UP_JINGLI_NAME= "经理";
    public final static String UP_ZONGJINGLI_NAME = "总经理";
    public final static String UP_BOSS_NAME = "老板";



    public  static int getRate(String key) {
        if (rateMap == null) {
            rateMap = new HashMap<>();
            rateMap.put(UP_YUANGONG, R.mipmap.up_yuangong);
            rateMap.put(UP_DIANZHANG, R.mipmap.up_dianzhang);
            rateMap.put(UP_JINGLI, R.mipmap.up_jingli);
            rateMap.put(UP_ZONGJINGLI, R.mipmap.up_zongjingli);
            rateMap.put(UP_BOSS, R.mipmap.up_boss);
        }
        if (TextUtils.isEmpty(key)) {
            return R.mipmap.up_yuangong;
        }
        return rateMap.get(key);
    }

    public  static int getRateForRank(String key) {
        if (rateMap2 == null) {
            rateMap2 = new HashMap<>();
            rateMap2.put(UP_YUANGONG, 1);
            rateMap2.put(UP_DIANZHANG, 2);
            rateMap2.put(UP_JINGLI, 3);
            rateMap2.put(UP_ZONGJINGLI, 4);
            rateMap2.put(UP_BOSS, 5);
        }
        if (TextUtils.isEmpty(key)) {
            return 1;
        }
        return rateMap2.get(key);
    }
}
