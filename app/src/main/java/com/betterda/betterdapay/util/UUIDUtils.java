package com.betterda.betterdapay.util;

import java.util.UUID;

/**
 * Created by Administrator on 2016/6/29.
 */
public class UUIDUtils {

    /**
     * 15      * 获得一个去掉"-"符号的UUID
     * 16      *
     * 17      * @return String UUID
     * 18
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉"-"符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得一个去掉"-"符号的UUID
     *
     * @return
     */
    public static String getUuid() {
        String s = UUID.randomUUID().toString();
        // 去掉"-"符号
        return s.replace("-", "");
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            if (i % 2 == 0) {
                ss[i] = getUuid();
            } else {
                ss[i] = getUUID();
            }
        }
        return ss;
    }
}
