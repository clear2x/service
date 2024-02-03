package com.security.handler;

import com.core.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求失败
 *
 * @author yuangy
 * @create 2020/6/29 17:03
 */
@Slf4j
public class ServiceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        Response.CLIENT.UN_AUTHORIZED.response(request.getRequestURL().toString(), response, HttpServletResponse.SC_UNAUTHORIZED);
        log.debug("客户端访问{}请求失败: {}", request.getRequestURI(), Response.CLIENT.UN_AUTHORIZED.getMessage(), authException);
    }
}
