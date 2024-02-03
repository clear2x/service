package com.security.configure;

import com.security.interceptor.ServiceProtectInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author yuangy
 * @create 2020/6/29 18:09
 */
@RequiredArgsConstructor
public class SecurityInterceptorConfigure implements WebMvcConfigurer {

    private final ServiceProtectInterceptor serviceProtectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serviceProtectInterceptor);
    }
}
