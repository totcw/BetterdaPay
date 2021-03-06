package com.betterda.betterdapay.data;

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
    public final static String UP_YUANGONG = "员工";
    public final static String UP_DIANZHANG = "店长";
    public final static String UP_JINGLI= "经理";
    public final static String UP_ZONGJINGLI = "总经理";
    public final static String UP_BOSS = "老板";

    public  static int getRate(String key) {
        if (rateMap == null) {
            rateMap = new HashMap<>();
            rateMap.put(UP_YUANGONG, R.mipmap.up_yuangong);
            rateMap.put(UP_DIANZHANG, R.mipmap.up_dianzhang);
            rateMap.put(UP_JINGLI, R.mipmap.up_jingli);
            rateMap.put(UP_ZONGJINGLI, R.mipmap.up_zongjingli);
            rateMap.put(UP_BOSS, R.mipmap.up_boss);
        }

        return rateMap.get(key);
    }

}
