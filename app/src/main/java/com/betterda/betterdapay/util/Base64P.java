package com.betterda.betterdapay.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/4/15.
 */
public class Base64P {

        // 加密
        public static String getBase64(String str) {
            byte[] b = null;
            String s = null;
                try {
                    b = str.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            if (b != null) {
                s = new String(Base64.encode(b,Base64.NO_WRAP));

            }
            return s;
        }

        // 解密
        public static String getFromBase64(String s) {
            byte[] b = null;
            String result = null;
            if (s != null) {

                try {

                    b = Base64.decode(s.getBytes("utf-8"), Base64.NO_WRAP);
                    result = new String(b, "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

}
