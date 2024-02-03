package com.apm.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yuangy
 * @create 2020-08-10 16:41
 */
@EnableAdminServer
@SpringBootApplication
public class ApmAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApmAdminServerApplication.class, args);
    }

}
