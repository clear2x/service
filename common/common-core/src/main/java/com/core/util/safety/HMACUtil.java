package com.core.util.safety;

import com.core.util.HexUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * HMAC摘要算法
 *
 * @author yuangy
 * @create 2020/6/4 11:14
 */
public class HMACUtil {

    private static final String HmacMD5 = "HmacMD5";
    private static final String HmacSHA1 = "HmacSHA1";
    private static final String HmacSHA256 = "HmacSHA256";
    private static final String HmacSHA384 = "HmacSHA384";
    private static final String HmacSHA512 = "HmacSHA512";

    /**
     * HmacSHA256摘要
     *
     * @param message 信息
     * @param secret  秘钥
     * @return 摘要字符串
     */
    public static String hmacSHA256(String message, String secret) {
        return hmac(message, secret, HmacSHA256);
    }

    /**
     * HmacSHA512摘要
     *
     * @param message 信息
     * @param secret  秘钥
     * @return 摘要字符串
     */
    public static String hmacSHA512(String message, String secret) {
        return hmac(message, secret, HmacSHA512);
    }

    public static String hmac(String message, String hexSecret, String algorithm) {
        String hash;
        try {
            SecretKeySpec secretKey = KeyUtil.restoreKey(hexSecret, algorithm);
            hash = hmac(message, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Error hmac：", e);
        }
        return hash;
    }

    public static String hmac(String message, SecretKeySpec secretKeySpec) {
        String hash;
        try {
            Mac hmac = Mac.getInstance(secretKeySpec.getAlgorithm());
            hmac.init(secretKeySpec);
            byte[] bytes = hmac.doFinal(message.getBytes());
            hash = HexUtil.byteArrToHexStr(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error hmac：", e);
        }
        return hash;
    }

    /**
     * algorithm 可以支持的值除了参考官方文档，还可以通过如下代码得出
     *
     * @param serviceName 可选值：Signature, MessageDigest, Cipher, Mac, KeyStore
     */
    public static void algorithms(String serviceName) {
        Security.getAlgorithms(serviceName).forEach(System.out::println);
    }

    public static void main(String[] args) {
        String message = "hello world";

        // key
        String hmacMD5 = KeyUtil.generateKey(HmacMD5);
        String hmacSHA1 = KeyUtil.generateKey(HmacSHA1);
        String hmacSHA256 = KeyUtil.generateKey(HmacSHA256);
        String hmacSHA384 = KeyUtil.generateKey(HmacSHA384);
        String hmacSHA512 = KeyUtil.generateKey(HmacSHA512);

        System.out.println(hmacMD5 + "    ==> " + hmacMD5.length());
        System.out.println(hmacSHA1 + "    ==> " + hmacSHA1.length());
        System.out.println(hmacSHA256 + "    ==> " + hmacSHA256.length());
        System.out.println(hmacSHA384 + "    ==> " + hmacSHA384.length());
        System.out.println(hmacSHA512 + "    ==> " + hmacSHA512.length());
        System.out.println("============");

        System.out.println(hmac(message, hmacMD5, HmacMD5));
        System.out.println(hmac(message, hmacSHA1, HmacSHA1));
        System.out.println(hmacSHA256(message, hmacSHA256));
        System.out.println(hmacSHA512(message, hmacSHA512));
        System.out.println(hmac(message, hmacSHA256, "HMACSHA256"));
        System.out.println(hmac(message, hmacSHA384, HmacSHA384));
        System.out.println(hmac(message, hmacSHA512, HmacSHA512));

        System.out.println("===============");
        System.out.println(KeyUtil.generateKey("AES"));

        System.out.println("===============");
        algorithms("KeyStore");
    }
}
