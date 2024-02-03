package com.api.system.service.fallback;

import com.api.system.service.IHelloService;
import com.core.constant.ServerConstant;
import com.core.http.Response;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceFallback implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable throwable) {
//        return new IHelloService() {
//            @Override
//            public String hello(String name) {
//                log.error("调用service-system服务出错", throwable);
//                return "调用出错";
//            }
//        };
        // 因为只需要实现一个接口，简化成lambada
        return name -> {
            log.error("调用{}服务出错", ServerConstant.SERVICE_SYSTEM, throwable);
            return Response.SERVER.TIMEOUT.setDescription(ServerConstant.SERVICE_SYSTEM + Response.SERVER.TIMEOUT.getMessage());
        };
    }
}
