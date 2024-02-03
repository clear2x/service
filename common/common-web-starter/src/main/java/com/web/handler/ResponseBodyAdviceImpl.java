package com.web.handler;

import com.core.http.Response;
import com.core.util.HttpRequestPathUtil;
import com.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 统一处理返回体
 *
 * @author yuangy
 * @create 2020-04-03 11:18
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
public class ResponseBodyAdviceImpl implements ResponseBodyAdvice<Object> {

    /**
     * 不需要处理返回数据的uri
     */
    private static final Set<String> ANON_URIS = new HashSet<>();

    static {
        // 文档相关
        ANON_URIS.add("/swagger-resources/**");
        ANON_URIS.add("/v2/api-docs/**");
        ANON_URIS.add("/v2/api-docs-ext/**");
        ANON_URIS.add("/doc.html/**");
        ANON_URIS.add("/webjars/**");
        // 监控相关
        ANON_URIS.add("/actuator/**");
    }


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 不需要封装的URI
        if (HttpRequestPathUtil.matchPath(request.getURI().getPath(), ANON_URIS)) {
            return body;
        }

        if (body instanceof ResponseEntity || body instanceof Response) {
            return body;
        } else if (body instanceof Map && ((Predicate<Object>) o -> {
            Map map = (Map) o;
            return map.containsKey("timestamp") && map.containsKey("status") && map.containsKey("error") && map.containsKey("message") && map.containsKey("path");
        }).test(body)) {
            return Response.CLIENT.ACCESS_SERVICE_DENIED.setData(body);
        } else if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JsonUtil.toJson(Response.success(body));
        } else {
            return Response.success(body);
        }
    }

}
