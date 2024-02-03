package com.api.system.configure;

import com.api.system.service.fallback.HelloServiceFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuangy
 * @create 2020-07-06 10:59
 */
@Configuration
public class ApiSystemAutoConfigure {

    @Bean
    public HelloServiceFallback helloServiceFallback() {
        return new HelloServiceFallback();
    }

}
