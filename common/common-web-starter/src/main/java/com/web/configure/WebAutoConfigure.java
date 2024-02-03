package com.web.configure;

import com.web.aspect.ControllerEndpointAspect;
import com.web.handler.GlobalExceptionHandler;
import com.web.handler.ResponseBodyAdviceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuangy
 * @create 2020-06-30 14:17
 */
@Configuration
public class WebAutoConfigure {
    @Bean
    public ControllerEndpointAspect controllerEndpointAspect() {
        return new ControllerEndpointAspect();
    }

    @Bean
    public GlobalExceptionHandler baseExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public ResponseBodyAdviceImpl responseBodyAdviceImpl() {
        return new ResponseBodyAdviceImpl();
    }

    @Bean
    public CustomizationConfigure customizationConfigure() {
        return new CustomizationConfigure();
    }

}
