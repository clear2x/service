package com.security.interceptor;

import com.core.constant.ServiceConstant;
import com.core.http.Response;
import com.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuangy
 * @create 2020/7/1 15:47
 */
@RequiredArgsConstructor
public class ServiceProtectInterceptor implements HandlerInterceptor {

    private final SecurityProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }
        String token = request.getHeader(ServiceConstant.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(ServiceConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            Response.CLIENT.DENIED_GATEWAY.response(request.getRequestURL().toString(), response, HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }

}
