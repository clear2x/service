package com.core.util.safety;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author yuangy
 */
public class RSAUtil {

    private static final String ALGORITHM = "RSA";
    /**
     * <p>参考：https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Signature</p>
     * <p>算法(Algorithm)/工作模式(Mode)/转换模式（transformation）</p>
     * <p>工作模式：</p>
     * <p>在AES中：</p>
     * <ul>
     *     <li>1. CBC（密码分组链接模式）：CBC 模式则比 ECB 模式多了一个初始向量 IV，加密的时候，第一个明文块会首先和初始向量 IV 做异或操作，然后再经过密钥加密，然后第一个密文块又会作为第二个明文块的加密向量来异或，依次类推下去，这样相同的明文块加密出的密文块就是不同的，明文的结构和密文的结构也将是不同的，因此更加安全，我们常用的就是 CBC 加密模式。</li>
     *     <li>2. ECB（电子密码本模式）：仅仅使用明文和密钥来加密数据，相同的明文块会被加密成相同的密文块，这样明文和密文的结构将是完全一样的。</li>
     *     <li>3. CFB</li>
     *     <li>4. OFB</li>
     * </ul>
     * <p>转换模式：</p>
     * <p>在AES中：</p>
     * <ul>
     *     <li>PKCS5：是指分组数据缺少几个字节，就在数据的末尾填充几个字节的几，比如缺少 5 个字节，就在末尾填充 5 个字节的 5。</li>
     *     <li>PKCS7：是指分组数据缺少几个字节，就在数据的末尾填充几个字节的 0，比如缺少 7 个字节，就在末尾填充 7 个字节的 0。</li>
     *     <li>NoPadding：是指不需要填充，也就是说数据的发送方肯定会保证最后一段数据也正好是 16 个字节。</li>
     * </ul>
     * <p>例子：</p>
     * <li>AES/CBC/NoPadding                         </li>
     * <li>AES/CBC/PKCS5Padding                      </li>
     * <li>AES/ECB/NoPadding                         </li>
     * <li>AES/ECB/PKCS5Padding                      </li>
     * <li>DES/CBC/NoPadding                         </li>
     * <li>DES/CBC/PKCS5Padding                      </li>
     * <li>DES/ECB/NoPadding                         </li>
     * <li>DES/ECB/PKCS5Padding                      </li>
     * <li>DESede/CBC/NoPadding                      </li>
     * <li>DESede/CBC/PKCS5Padding                   </li>
     * <li>DESede/ECB/NoPadding                      </li>
     * <li>DESede/ECB/PKCS5Padding                   </li>
     * <li>RSA/ECB/PKCS1Padding                      </li>
     * <li>RSA/ECB/OAEPWithSHA-1AndMGF1Padding       </li>
     * <li>RSA/ECB/OAEPWithSHA-256AndMGF1Padding     </li>
     */
    private static final String CIPHER_KEY = "RSA/ECB/PKCS1Padding";
    /**
     * <p>参考值：</p>
     * <ul>
     *     <li>NONEwithRSA</li>
     *     <li>MD2withRSA</li>
     *     <li>MD5withRSA</li>
     *     <li>SHA1withRSA</li>
     *     <li>SHA256withRSA</li>
     *     <li>SHA384withRSA</li>
     *     <li>SHA512withRSA</li>
     * </ul>
     */
    public final static String DEFAULT_SIGNATURE_ALGORITHM = "SHA1withRSA";
    public final static int DEFAULT_SIZE_1024 = 1024;
//    public final static int DEFAULT_SIZE_2048 = 2048;

    // 生成秘钥对
    public static RSA genKeyPair(int keySize) {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(keySize, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

            // 得到公钥字符串
            String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            RSA rsa = new RSA();
            rsa.setPrivateKey(privateKeyString);
            rsa.setPublicKey(publicKeyString);
            return rsa;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     ************************************* 公钥加密/私钥解密 *****************************************************
     */

    /**
     * RSA公钥加密（数据加密）
     *
     * @param data      加密内容
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            Key key = getPublicKey(publicKey);
            //RSA加密
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " publicKey: " + publicKey, e);
        }
    }

    /**
     * RSA私钥解密（数据加密）
     *
     * @param data       加密字符串
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decryptByPrivateKey(String data, String privateKey) {
        try {
            Key key = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " privateKey: " + privateKey, e);
        }
    }

    /*
     ************************************* 私钥加密/公钥解密 *****************************************************
     */

    /**
     * RSA私钥加密
     *
     * @param data       加密内容
     * @param privateKey 私钥
     * @return 密文
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            Key key = getPrivateKey(privateKey);
            //RSA加密
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " privateKey: " + privateKey, e);
        }
    }

    /**
     * RSA公钥解密
     *
     * @param data      加密字符串
     * @param publicKey 私钥
     * @return 明文
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            Key key = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " publicKey: " + publicKey, e);
        }
    }

    /*
     ************************************* 签名 *****************************************************
     */

    public static String sign(String data, String privateKey) {
        return sign(data, privateKey, DEFAULT_SIGNATURE_ALGORITHM);
    }

    public static boolean signVerify(String data, String publicKey, String sign) {
        return signVerify(data, publicKey, sign, DEFAULT_SIGNATURE_ALGORITHM);
    }

    /**
     * 使用私钥进行签名
     *
     * @param data       待签名字符串
     * @param privateKey 私钥
     * @return 签名密文
     */
    public static String sign(String data, String privateKey, String signAlgorithm) {
        try {
            PrivateKey k = (PrivateKey) getPrivateKey(privateKey);
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(k);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " privateKey: " + privateKey + " signAlgorithm: " + signAlgorithm, e);
        }
    }

    /**
     * 使用公钥进行签名验证
     *
     * @param data      签名数据
     * @param publicKey 公钥
     * @return 校验是否正确
     * @
     */
    public static boolean signVerify(String data, String publicKey, String sign, String signAlgorithm) {
        try {
            PublicKey k = (PublicKey) getPublicKey(publicKey);
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(k);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("失败，data: " + data + " publicKey: " + publicKey + " signAlgorithm: " + signAlgorithm, e);
        }
    }

    /*
     ************************************* 其他 *****************************************************
     */

    private static Key getPublicKey(String key) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Key getPrivateKey(String key) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class RSA {
        private String privateKey;
        private String publicKey;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        @Override
        public String toString() {
            return "RSA{" +
                    "privateKey='" + privateKey + '\'' +
                    ", publicKey='" + publicKey + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        RSA rsa = genKeyPair(DEFAULT_SIZE_1024);

        assert rsa != null;
        System.out.println("私钥：" + rsa.getPrivateKey());
        System.out.println("公钥：" + rsa.getPublicKey());

        String data = "Hello,RSA,Hello,RSAHello,RSAHello,RSAHello,RSAHello,RSAHello,RSA";

        System.out.println("---------------公钥加密，私钥解密-----------------");
        String encryptedData = encryptByPublicKey(data, rsa.getPublicKey());
        System.out.println("加密后：" + encryptedData);
        String decryptedData = decryptByPrivateKey(encryptedData, rsa.getPrivateKey());
        System.out.println("解密后：" + decryptedData);

        System.out.println("---------------私钥加密，公钥解密-----------------");
        encryptedData = encryptByPrivateKey(data, rsa.getPrivateKey());
        System.out.println("加密后：" + encryptedData);
        decryptedData = decryptByPublicKey(encryptedData, rsa.getPublicKey());
        System.out.println("解密后：" + decryptedData);

        String sign = sign(data, rsa.getPrivateKey());
        System.out.println("签名：" + sign);
        System.out.println("签名验证：" + signVerify(data, rsa.getPublicKey(), sign));

    }

}
