package com.gateway.controller;

import com.core.http.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 首页
 *
 * @author yuangy
 * @create 2020/7/7 15:56
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public Mono<Response> index() {
        return Mono.just(Response.SUCCESS.SUCCESS.setData("Service Cloud Gateway"));
    }
}
