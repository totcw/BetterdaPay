package com.betterda.betterdapay.data;

import com.betterda.betterdapay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行卡的数据
 * Created by Administrator on 2016/8/18.
 */
public class BankData {
    public  static   Map<String,Integer> bankMap;
    public  static   List<String> bankList;

    public  static int getBank(String key) {
        if (bankMap == null) {
            bankMap = new HashMap<>();
            bankMap.put("中国农业银行", R.mipmap.nongye);
            bankMap.put("中国工商银行", R.mipmap.gongshang);
            bankMap.put("中国建设银行", R.mipmap.jianshe);
            bankMap.put("中国交通银行", R.mipmap.jiaotong);
            bankMap.put("中国人民银行", R.mipmap.renmin);
            bankMap.put("中国招商银行", R.mipmap.zhaoshang);
            bankMap.put("兴业银行", R.mipmap.xingye);
            bankMap.put("中国邮政银行", R.mipmap.youzheng);
            bankMap.put("中国银行", R.mipmap.zhongguo);
            bankMap.put("中信银行", R.mipmap.zhongxin);
            bankMap.put("民生银行", R.mipmap.minsheng);
        }

        return bankMap.get(key);
    }


    public static  List  getBankList() {
        if (bankList == null) {
            bankList = new ArrayList<>();
            bankList.add("中国农业银行");
            bankList.add("中国工商银行");
            bankList.add("中国建设银行");
            bankList.add("中国交通银行");
            bankList.add("中国人民银行");
            bankList.add("中国招商银行");
            bankList.add("中国邮政银行");
            bankList.add("中国银行");
            bankList.add("中信银行");
            bankList.add("民生银行");
            bankList.add("兴业银行");
        }
        return bankList;
    }

}
