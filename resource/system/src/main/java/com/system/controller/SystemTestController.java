package com.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 系统服务测试
 *
 * @author yuangy
 * @create 2020/7/13 11:50
 */
@Slf4j
@RestController
public class SystemTestController {

    /**
     * 服务信息
     *
     * @return 服务信息
     */
    @GetMapping("info")
    public String test() {
        return "service-system";
    }

    /**
     * 用户信息
     *
     * @return 用户信息
     */
    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    /**
     * hello
     *
     * @param name 名称
     * @return hello名称
     */
    @GetMapping("hello")
    public String hello(String name) {
        log.info("/hello服务被调用");
        return "hello" + name;
    }
}
