package com.feign.configure;

import com.core.constant.ServiceConstant;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Configuration
// 只有当配置了：hystrix.shareSecurityContext=true时，才加载。默认matchIfMissing=false加载，配置把SecurityContext对象从你当前主线程传输到Hystrix线程
@ConditionalOnProperty(value = "hystrix.shareSecurityContext", havingValue = "true")
public class FeignAutoConfigure {
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, ServiceConstant.OAUTH2_TOKEN_TYPE + " " + authorizationToken);
            }
        };
    }
}
