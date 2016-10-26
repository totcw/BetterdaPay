package com.betterda.betterdapay.util;

import android.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * RSA加密
 * Created by Administrator on 2016/8/18.
 */
public class RSA {

    /**
     * 加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return 加密并Base64.encode后字符
     * @throws Exception 加密过程中的异常信息
     */

    public String encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {

            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return Base64.encodeToString(output,Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            throw new Exception("无此加密算法");
        } catch (InvalidKeyException e) {

            throw new Exception("加密公钥非法,请检查");

        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {

            throw new Exception("明文数据已损坏");

        }

    }



    /**

     *AES加密 * @param src 待加密明文

     * @param key 秘钥 * @return 加密后的字符 */
    public static String encrypt(String src, String key) throws Exception { try {

        String e = "0000000000000000";

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding"); int blockSize = cipher.getBlockSize();
        byte[] dataBytes = src.getBytes("UTF-8"); int plaintextLength = dataBytes.length; if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));

        }

        byte[] plaintext = new byte[plaintextLength]; System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length); SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES"); IvParameterSpec ivspec = new IvParameterSpec(e.getBytes()); cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext); return Base64.encodeToString(encrypted,Base64.NO_WRAP);
    } catch (Exception ex) {


        throw new Exception("aes解密失败");

    }

    }
}
