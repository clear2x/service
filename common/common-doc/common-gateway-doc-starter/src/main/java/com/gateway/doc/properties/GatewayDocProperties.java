package com.gateway.doc.properties;

import com.core.constant.ServiceConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ServiceConstant.PROPERTIES_PREFIX + "gateway.doc")
@Data
public class GatewayDocProperties {

    /**
     * 是否开启网关文档聚合功能
     */
    private Boolean enable;

    /**
     * 需要暴露doc的服务列表，多个值时用逗号分隔
     * 如 Auth,System
     */
    private String resources;


}
