package com.core.util.safety;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * @author yuangy
 * @create 2020-06-03 12:08
 */
public class AESUtil {

    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final String IV_STR = "0123456789ABCDEF";

    public static String encrypt(String plainText, String key) {
        return encrypt(plainText, key, IV_STR);
    }

    public static String decrypt(String cipherText, String key) {
        return decrypt(cipherText, key, IV_STR);
    }

    public static String generate16Key() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16).toUpperCase();
    }

    /**
     * AES加密
     *
     * @param plainText 明文
     * @param key       16位对称密钥
     * @param ivStr     16位向量
     * @return 密文
     */
    public static String encrypt(String plainText, String key, String ivStr) {

        checkParam(plainText, key, ivStr);

        try {
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), AES_ALG), iv);

            byte[] encryptBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败，plainText=" + plainText +
                    "，keySize=" + key.length() + "。" + e.getMessage(), e);
        }
    }

    /**
     * AES解密
     *
     * @param cipherText 密文
     * @param key        16位对称密钥
     * @param ivStr      16位向量
     * @return 明文
     */
    public static String decrypt(String cipherText, String key, String ivStr) {

        checkParam(cipherText, key, ivStr);

        try {
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), AES_ALG), iv);

            byte[] cleanBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
            return new String(cleanBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败，cipherText=" + cipherText +
                    "，keySize=" + key.length() + "。" + e.getMessage(), e);
        }
    }

    private static void checkParam(String text, String key, String ivStr) {
        if (text == null) {
            throw new RuntimeException("加密或解密格式不正确：null");
        }
        if (key == null || key.length() != 16) {
            throw new RuntimeException("AES对称秘钥格式不正确，请确保秘钥key[" + key + "]不为NULL，长度为16位。");
        }
        if (ivStr == null || ivStr.length() != 16) {
            throw new RuntimeException("AES向量格式不正确，请确保向量ivStr[" + ivStr + "]不为NULL，长度为16位。");
        }
    }

    public static void main(String[] args) {
        String key = generate16Key();

        System.out.println("秘钥为：" + key);
        System.out.println("秘钥为：" + generate16Key());
        System.out.println("秘钥为：" + generate16Key());
        System.out.println("秘钥为：" + generate16Key());

        String value = "hello world zhangSan liSi > < &";

        String encrypt = encrypt(value, key);
        System.out.println("加密：" + encrypt);
        String decrypt = decrypt(encrypt, key);
        System.out.println("解密：" + decrypt);
    }

}
