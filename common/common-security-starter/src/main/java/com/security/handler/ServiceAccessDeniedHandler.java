package com.security.handler;

import com.core.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务器异常：403：用户无权限返回
 */
@Slf4j
public class ServiceAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        Response.CLIENT.ACCESS_DENIED.response(request.getRequestURL().toString(), response, HttpServletResponse.SC_FORBIDDEN);
        log.debug("没有权限访问：", accessDeniedException);
    }
}
