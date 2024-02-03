package com.core.util.safety;

import com.core.util.HexUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author yuangy
 * @create 2020-06-05 10:30
 */
public class KeyUtil {

    /**
     * 生成key
     *
     * @param algorithm 算法
     * @return key
     */
    public static String generateKey(String algorithm) {
        try {
            // 初始化HmacMD5摘要算法的密钥产生器
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            // 产生密钥
            SecretKey secretKey = generator.generateKey();
            // 获得密钥
            byte[] key = secretKey.getEncoded();
            return HexUtil.byteArrToHexStr(key);
        } catch (Exception e) {
            throw new RuntimeException("不支持此算法" + algorithm + "：", e);
        }
    }

    /**
     * 还原key
     *
     * @param hexSecret 16进制秘钥
     * @param algorithm 算法
     * @return 还原的key
     */
    public static SecretKeySpec restoreKey(String hexSecret, String algorithm) {
        return new SecretKeySpec(HexUtil.hexStrToByteArr(hexSecret), algorithm);
    }

}
