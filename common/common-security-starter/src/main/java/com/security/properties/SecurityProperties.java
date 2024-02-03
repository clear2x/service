package com.security.properties;

import com.core.constant.EndpointConstant;
import com.core.constant.ServiceConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuangy
 * @create 2020-06-29 14:31
 */
@Data
@ConfigurationProperties(ServiceConstant.PROPERTIES_PREFIX + "security")
public class SecurityProperties {

    /**
     * 是否是授权（认证）中心
     */
    private Boolean isAuthCenter = Boolean.FALSE;
    /**
     * 是否开启安全配置
     */
    private Boolean enable = Boolean.TRUE;
    /**
     * 配置需要认证的uri，默认为所有/**
     */
    private String authUri = EndpointConstant.ALL;
    /**
     * 配置需要什么scope才可访问，如果是允许多个scope，中间用","逗号分隔，例如：app,web
     */
    private String scope;
    /**
     * 免认证资源路径，支持通配符
     * 多个值时使用逗号分隔
     */
    private String anonUris;
    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;

}
