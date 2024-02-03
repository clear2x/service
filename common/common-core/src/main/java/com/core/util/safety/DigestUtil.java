package com.core.util.safety;

import java.security.MessageDigest;

/**
 * 摘要算法
 *
 * @author yuangy
 * @create 2020-06-02 18:04
 */
public class DigestUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String digest(String algorithm, String input, String charset) throws Exception {
        byte[] bytes = MessageDigest.getInstance(algorithm).digest(input.getBytes(charset));
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            // 转换成16进制
            sb.append(String.format("%02x", aByte & 0xFF));
        }
        return sb.toString();
    }

    // 计算 SHA-1 摘要
    public static String sha1(String input, String charset) throws Exception {
        return digest("SHA-1", input, charset);
    }

    // 计算 SHA-1 摘要
    public static String sha1(String input) throws Exception {
        return sha1(input, DEFAULT_CHARSET);
    }

    // 计算 SHA-1 摘要
    public static String sha1Quietly(String input) {
        try {
            return sha1(input);
        } catch (Exception e) {
            return null;
        }
    }

    // 计算 SHA-256 摘要
    public static String sha256(String input, String charset) throws Exception {
        return digest("SHA-256", input, charset);
    }

    // 计算 SHA-256 摘要
    public static String sha256(String input) throws Exception {
        return sha256(input, DEFAULT_CHARSET);
    }

    // 计算 SHA-256 摘要
    public static String sha256Quietly(String input) {
        try {
            return sha256(input);
        } catch (Exception e) {
            return null;
        }
    }

    // 计算 MD5 摘要
    public static String md5(String input, String charset) throws Exception {
        return digest("MD5", input, charset);
    }

    // 计算 MD5 摘要
    public static String md5(String input) throws Exception {
        return md5(input, DEFAULT_CHARSET);
    }

    // 计算 MD5 摘要
    public static String md5Quietly(String input) {
        try {
            return md5(input);
        } catch (Exception e) {
            return null;
        }
    }

}
