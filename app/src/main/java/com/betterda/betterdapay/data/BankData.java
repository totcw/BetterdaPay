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
    public  static   Map<String,String> bankNoMap;
    public  static   List<String> bankList;

    public  static int getBank(String key) {
        if (bankMap == null) {
            bankMap = new HashMap<>();
            bankMap.put("中国工商银行", R.mipmap.gongshang);
            bankMap.put("中国农业银行", R.mipmap.nongye);
            bankMap.put("中国银行", R.mipmap.zhongguo);
            bankMap.put("中国建设银行", R.mipmap.jianshe);
            bankMap.put("中国交通银行", R.mipmap.jiaotong);
            bankMap.put("中信银行", R.mipmap.zhongxin);
            bankMap.put("民生银行", R.mipmap.minsheng);
            bankMap.put("兴业银行", R.mipmap.xingye);
            bankMap.put("中国邮政储蓄银行", R.mipmap.youzheng);
            bankMap.put("上海浦东发展银行", R.mipmap.pudong);
            bankMap.put("中国光大银行", R.mipmap.guangda);
            bankMap.put("中国招商银行", R.mipmap.zhaoshang);
            bankMap.put("平安银行", R.mipmap.pingan);
            bankMap.put("广发银行", R.mipmap.guangfa);
            bankMap.put("华夏银行", R.mipmap.huaxia);
        }

        return bankMap.get(key);
    }

    /**
     * 获取联行号
     * @param key
     * @return
     */
    public  static String getBankNo(String key) {
        if (bankNoMap == null) {
            bankNoMap = new HashMap<>();
            bankNoMap.put("中国工商银行", "102100099996");
            bankNoMap.put("中国农业银行", "103100000026");
            bankNoMap.put("中国银行", "104100000004");
            bankNoMap.put("中国建设银行", "105100000017");
            bankNoMap.put("中国交通银行", "301290000007");
            bankNoMap.put("中信银行", "302100011000");
            bankNoMap.put("民生银行", "305100000013");
            bankNoMap.put("兴业银行", "309391000011");
            bankNoMap.put("中国邮政储蓄银行", "403100000004");
            bankNoMap.put("上海浦东发展银行", "310290000013");
            bankNoMap.put("中国光大银行", "303100000006");
            bankNoMap.put("中国招商银行", "308584000013");
            bankNoMap.put("平安银行", "307584007998");
            bankNoMap.put("广发银行", "306581000003");
            bankNoMap.put("华夏银行", "304100040000");
        }

        return bankNoMap.get(key);
    }

    public static  List  getBankList() {
        if (bankList == null) {
            bankList = new ArrayList<>();
            bankList.add("中国工商银行");
            bankList.add("中国农业银行");
            bankList.add("中国银行");
            bankList.add("中国建设银行");
            bankList.add("中国交通银行");
            bankList.add("中信银行");
            bankList.add("民生银行");
            bankList.add("兴业银行");
            bankList.add("中国邮政储蓄银行");
            bankList.add("上海浦东发展银行");
            bankList.add("中国光大银行");
            bankList.add("中国招商银行");
            bankList.add("平安银行");
            bankList.add("广发银行");
            bankList.add("华夏银行");
        }
        return bankList;
    }

}
