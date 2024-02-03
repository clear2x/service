package com.security.annotation;

import com.security.configure.ResourceServerConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ResourceServerConfigure.class)
public @interface EnableResourceServer {
}
