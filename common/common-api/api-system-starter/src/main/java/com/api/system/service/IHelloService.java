package com.api.system.service;

import com.api.system.service.fallback.HelloServiceFallback;
import com.core.constant.ServerConstant;
import com.core.http.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * value指定远程服务的名称，这个名称对应service-system模块配置文件application.yml里spring.application.name的配置，即注册到Eureka里的服务名称<br>
 * contextId指定这个Feign Client的别名，当我们定义了多个Feign Client并且value值相同（即调用同一个服务）的时候，需要手动通过contextId设置别名，否则程序将抛出异常<br>
 * fallbackFactory指定了回退方法，当我们调用远程服务出现异常时，就会调用这个回退方法。fallback也可以指定回退方法，但fallbackFactory指定的回退方法里可以通过Throwable对象打印出异常日志，方便分析问题<br>
 * <p>
 * 如何使用：
 * 1. 在调用方的入口方法上标注注解：@EnableFeignClients <br>
 * 2. 在需要使用的地方，像使用普通方法一样使用<br>
 * </p>
 */
@FeignClient(value = ServerConstant.SERVICE_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {
    @GetMapping("hello")
    Response hello(@RequestParam("name") String name);
}
