package com.core.constant;

/**
 * @author yuangy
 * @create 2020-06-29 17:25
 */
public interface ServiceConstant {
    /**
     * 配置统一前缀
     */
    String PROPERTIES_PREFIX = "self.";

    /**
     * Gateway请求头TOKEN名称（不要有空格）
     */
    String GATEWAY_TOKEN_HEADER = "GatewayToken";
    /**
     * Gateway请求头TOKEN值
     */
    String GATEWAY_TOKEN_VALUE = "service:gateway:123456";
    /**
     * 验证码 key前缀
     */
    String CODE_PREFIX = "service.captcha.";
    /**
     * Java默认临时目录
     */
    String JAVA_TEMP_DIR = "java.io.tmpdir";
    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    String[] VALID_FILE_TYPE = {"xlsx", "zip"};
    /**
     * utf-8
     */
    String UTF8 = "utf-8";
    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    String OAUTH2_TOKEN_TYPE = "bearer";
    /**
     * 注册用户角色ID
     */
    Long REGISTER_ROLE_ID = 2L;

}
