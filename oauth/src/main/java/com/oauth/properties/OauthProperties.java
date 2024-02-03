package com.oauth.properties;

import com.core.constant.ServiceConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuangy
 * @create 2020-06-30 10:53
 */
@Component
@ConfigurationProperties(ServiceConstant.PROPERTIES_PREFIX + "oauth")
@Data
public class OauthProperties {

    /**
     * 是否开启验证码功能
     */
    private Boolean enableCode = Boolean.TRUE;
    /**
     * 验证码配置
     */
    private ValidateCodeProperties code;
    /**
     * 是否启用密码安全传输（RSA加密）
     */
    private Boolean enablePasswordSecureTransport = Boolean.TRUE;
    /**
     * RSA公钥
     */
    private String rsaPublicKey;
    /**
     * RSA私钥
     */
    private String rsaPrivateKey;
    /**
     * 是否使用 JWT令牌
     */
    private Boolean enableJwt = Boolean.FALSE;
    /**
     * JWT加签密钥
     */
    private String jwtAccessKey = "66F815F290887CC231EB566D42112C1450C78C3EFE762EBECCB44D792F26588C";

}
