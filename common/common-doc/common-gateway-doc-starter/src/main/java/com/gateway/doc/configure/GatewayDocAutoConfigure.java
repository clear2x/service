package com.gateway.doc.configure;

import com.core.constant.ServiceConstant;
import com.gateway.doc.filter.GatewayDocHeaderFilter;
import com.gateway.doc.properties.GatewayDocProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;

@Configuration
@EnableConfigurationProperties(GatewayDocProperties.class)
@ConditionalOnProperty(value = ServiceConstant.PROPERTIES_PREFIX + "gateway.doc.enable", havingValue = "true", matchIfMissing = true)
public class GatewayDocAutoConfigure {

    private final GatewayDocProperties gatewayDocProperties;
    private SecurityConfiguration securityConfiguration;
    private UiConfiguration uiConfiguration;

    public GatewayDocAutoConfigure(GatewayDocProperties gatewayDocProperties) {
        this.gatewayDocProperties = gatewayDocProperties;
    }

    @Autowired(required = false)
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired(required = false)
    public void setUiConfiguration(UiConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    @Bean
    @SuppressWarnings("all")
    public GatewayDocResourceConfigure gatewayDocResourceConfigure(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        return new GatewayDocResourceConfigure(routeLocator, gatewayProperties);
    }

    @Bean
    public GatewayDocHeaderFilter gatewayDocHeaderFilter() {
        return new GatewayDocHeaderFilter();
    }

//    @Bean
//    public GatewayDocHandler gatewayDocHandler(SwaggerResourcesProvider swaggerResources) {
//        GatewayDocHandler gatewayDocHandler = new GatewayDocHandler();
//        gatewayDocHandler.setSecurityConfiguration(securityConfiguration);
//        gatewayDocHandler.setUiConfiguration(uiConfiguration);
//        gatewayDocHandler.setSwaggerResources(swaggerResources);
//        gatewayDocHandler.setProperties(gatewayDocProperties);
//        return gatewayDocHandler;
//    }
}
