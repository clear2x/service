package com.security.configure;

import com.core.constant.ServiceConstant;
import com.core.support.AuthUserInformation;
import com.security.handler.ServiceAccessDeniedHandler;
import com.security.handler.ServiceAuthExceptionEntryPoint;
import com.security.interceptor.ServiceProtectInterceptor;
import com.security.properties.SecurityProperties;
import com.security.service.AuthUserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yuangy
 * @create 2020-06-29 17:31
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(value = ServiceConstant.PROPERTIES_PREFIX + "security.enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class SecurityAutoConfigure {

    private final SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "serviceAccessDeniedHandler")
    public ServiceAccessDeniedHandler serviceAccessDeniedHandler() {
        return new ServiceAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "serviceAuthExceptionEntryPoint")
    public ServiceAuthExceptionEntryPoint exceptionEntryPoint() {
        return new ServiceAuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServiceProtectInterceptor serviceProtectInterceptor() {
        return new ServiceProtectInterceptor(securityProperties);
    }

    @Bean
    @ConditionalOnBean(ServiceProtectInterceptor.class)
    public SecurityInterceptorConfigure securityInterceptorConfigure() {
        return new SecurityInterceptorConfigure(serviceProtectInterceptor());
    }

    @Bean
    public AuthUserInformation authUserInformation() {
        return new AuthUserInformationService();
    }

//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor() {
//        return requestTemplate -> {
//            String gatewayToken = new String(Base64Utils.encode(ServiceConstant.GATEWAY_TOKEN_VALUE.getBytes()));
//            requestTemplate.header(ServiceConstant.GATEWAY_TOKEN_HEADER, gatewayToken);
//            String authorizationToken = ServiceUtil.getCurrentTokenValue();
//            if (StringUtils.isNotBlank(authorizationToken)) {
//                requestTemplate.header(HttpHeaders.AUTHORIZATION, ServiceConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
//            }
//        };
//    }

}
