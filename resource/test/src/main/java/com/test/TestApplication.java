package com.test;

import com.security.annotation.EnableResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yuangy
 * @create 2020-07-02 11:04
 */
@SpringBootApplication
@EnableResourceServer
@EnableFeignClients(basePackages = {"com.api.*.service"}) // 开启Feign，并扫描实例化的包
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
