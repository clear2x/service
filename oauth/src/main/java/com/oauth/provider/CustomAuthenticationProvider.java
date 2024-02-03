package com.oauth.provider;

import com.core.util.safety.RSAUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 自定义密码校验逻辑，主要是重写additionalAuthenticationChecks方法，参考DaoAuthenticationProvider <br>
 * 1. 添加的Provider会在默认的Provider（DaoAuthenticationProvider）之前，<br>
 * 2. 可以自行查看：org.springframework.security.authentication.ProviderManager.getProviders()方法，<br>
 * 3. ProviderManager对校验不通过的处理逻辑是：当Provider抛出BadCredentialsException异常，<br>
 * 4. 而抽象类AbstractUserDetailsAuthenticationProvider继续往上抛AuthenticationException，<br>
 * 5. ProviderManager会对AuthenticationException进行catch（而其他异常则会继续抛出去），继续循环交给下一个Provider，直到都遍历完成 <br>
 * <p>
 *     <ul>
 *         加密方法：
 *         <li>1. RSA加密</li>
 *     </ul>
 *     <ul>
 *         解密方法：
 *         <li>1. RSA解密</li>
 *     </ul>
 * </p>
 *
 * @author yuangy
 * @create 2020-08-06 15:39
 * @see DaoAuthenticationProvider
 */
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider<UsernamePasswordAuthenticationToken> {

    private final PasswordEncoder passwordEncoder;
    private final String rsaPrivateKey;

    public CustomAuthenticationProvider(Class<UsernamePasswordAuthenticationToken> tokenClass, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, String rsaPrivateKey) {
        super(tokenClass, userDetailsService);
        this.passwordEncoder = passwordEncoder;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();
        try {
            // 解密
            presentedPassword = RSAUtil.decryptByPrivateKey(presentedPassword, rsaPrivateKey);
        } catch (Exception e) {
            // 如果抛出RuntimeException异常，则ProviderManager直接抛出去，这样默认的DaoAuthenticationProvider则处理不到，从而不支持明文模式的密码登录
            // throw new RuntimeException("解密失败",e);
            throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        RSAUtil.RSA rsa = RSAUtil.genKeyPair(RSAUtil.DEFAULT_SIZE_1024);
        Assert.notNull(rsa, "rsa不能为null");
        String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI/pjrQtGgMbGTykTrpsX1gt7F0L2Hrj6U8NNM5DpOl89YAghXYB1DkSo5LammbGxL3Ful+mMjWKG9WF3hDF8EWTrexBook8zATZ5WUIC3+/a4KGrWs86uW1y/HhJZwmim1Q94rilaXOE/5uhIIb505ZfpDKf7go2AVN260NXoHjAgMBAAECgYAA/8cc2x9OHDcNo7pkMvJqg08bykRDfieVg+r076VKdbKipBcpHZQinU2DbH8b7Y9aHfBQT7ATjRTqY5aKGUW0w0pcbXyO70lMJm5uOCBCrT7G+DnSernTXgvynUCObMVfq/+JeBMvItqHkoNeHDVIA9d7Bn5Jm5vBO3cf3xHM+QJBAMKGDR4++nyZ3E7+2ifSp6KemG13bo8BeOHL1FT58BAUNkRWZbb/yI+UDaf2o2Tn5XbIqG5Q7UZxY1yWcVuMx78CQQC9ZMuIG92bfm52UaPho9s0LQEqNQcPo5sJRhK+tkNAxdxBZl43CIYDnGU3rZbvmpJzl5mbBam/UDd66nWJfm7dAkAjDougrtLvz77Pkch5MtK5UQHBgKuxJB/H+bcLlXu5OhtJl5R/NXvMvG71nVJ3Jf/Jt4H7V6RbdKXmkspdKHL7AkAHkY6cqthpTV0mt06ARucMp7vDLwlQM66PaVB4dJotbXrG1ktvkZShck+NMQ+2FYxCuE04J7rH3XHKJLJ7wyS1AkBeQtrZbIF2zTwF+t1w0DtXmQS0a5w5IWAnYcPrqlC9F3NySxq7Kxnt2EZXU4Zp7OrGEoGm8POicGSG73TOkr4e";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP6Y60LRoDGxk8pE66bF9YLexdC9h64+lPDTTOQ6TpfPWAIIV2AdQ5EqOS2ppmxsS9xbpfpjI1ihvVhd4QxfBFk63sQaKJPMwE2eVlCAt/v2uChq1rPOrltcvx4SWcJoptUPeK4pWlzhP+boSCG+dOWX6Qyn+4KNgFTdutDV6B4wIDAQAB";
        String encryptPassword = RSAUtil.encryptByPublicKey("1234qwer", pubKey);
        System.out.println(encryptPassword);
        String urlEncodePassword = URLEncoder.encode(encryptPassword, "UTF-8"); // KVBx2Jeeh6mN9VhRK5PNod3YilcXLRQd7CH67iE2orIIrhOEXYKJRFcYtahVU5eg%2BImclSYEfS0Gh3CtPO4v173SmeE%2BbYKgl7y6%2Bpjp1vnjAkG0mJooIyf4m6kRnjSe%2F3yN5V4tPqWh9ND65O7a%2BHmZT7%2BUOE1DOKfBK8rx4KU%3D
        System.out.println(urlEncodePassword);
        String urlDecodePassword = URLDecoder.decode(urlEncodePassword, "UTF-8");
        System.out.println(urlDecodePassword);
        String decryptPassword = RSAUtil.decryptByPrivateKey(encryptPassword, priKey);
        System.out.println(decryptPassword);
    }
}
