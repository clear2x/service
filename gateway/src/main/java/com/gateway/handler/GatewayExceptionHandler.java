package com.gateway.handler;

import com.core.http.Response;
import com.core.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * 网关异常处理
 *
 * @author yuangy
 * @create 2020/7/7 9:59
 */
@Slf4j
public class GatewayExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GatewayExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                   ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 异常处理，定义返回报文格式
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        log.error(
                "请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
                request.path(), request.methodName(), error.getMessage()
        );
        Map<String, Object> errorAttributes;
        String simpleMessage = ThrowableUtil.getSimpleMessage(error);
        if (error instanceof NotFoundException) {
            String serverId = StringUtils.substringAfterLast(error.getMessage(), "Unable to find instance for ");
            serverId = StringUtils.replace(serverId, "\"", StringUtils.EMPTY);
            errorAttributes = Response.SERVER.GATEWAY_FORWARD_UNABLE_TO_FIND_INSTANCE.setDescription(String.format("无法找到%s服务", serverId)).toMap();
        } else if (StringUtils.containsIgnoreCase(error.getMessage(), "connection refused")) {
            errorAttributes = Response.CLIENT.ACCESS_SERVICE_DENIED.setDescription(simpleMessage).toMap();
        } else if (error instanceof TimeoutException) {
            errorAttributes = Response.SERVER.TIMEOUT.setDescription(simpleMessage).toMap();
        } else if (error instanceof ResponseStatusException
                && StringUtils.containsIgnoreCase(error.getMessage(), HttpStatus.NOT_FOUND.toString())) {
            errorAttributes = Response.CLIENT.NOT_FOND.setDescription(simpleMessage).toMap();
        } else {
            errorAttributes = Response.SERVER.GATEWAY_FORWARD_EXCEPTION.setDescription(simpleMessage).toMap();
        }
        return errorAttributes;
    }

    @Override
    @SuppressWarnings("all")
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
