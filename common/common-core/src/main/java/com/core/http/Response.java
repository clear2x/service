package com.core.http;

import com.core.util.JsonUtil;
import com.core.util.StackTraceUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuangy
 * @create 2020-03-20 18:10
 */
@Slf4j
@SuppressWarnings("all")
public class Response {

    /**
     * 成功状态码
     */
    public interface SUCCESS {
        Response SUCCESS = new Response(200, "成功");
        Response LOGIN_SUCCESS = new Response(200, "登陆成功");
        Response LOGOUT_SUCCESS = new Response(200, "登出成功");
    }

    /**
     * 参数错误 10001~19999
     */
    public interface PARAM {

        /*
         *  可以使用：
         * 10101~10199表示业务1类的参数错误
         * 10201~10299表示业务2类的参数错误
         * ...
         * */

        /**
         * 统一普通参数错误
         */
        Response BAD_GENERAL_PARAMETER = new Response(10400, "参数错误");
        /**
         * 统一JAVA对象参数错误
         */
        Response BAD_BEAN_PARAMETER = new Response(10401, "参数错误");
        Response IS_INVALID = new Response(10001, "参数无效");
        Response IS_BLANK = new Response(10002, "参数为空");
        Response TYPE_BIND_ERROR = new Response(10003, "参数类型错误");
        Response NOT_COMPLETE = new Response(10004, "参数缺失");
        Response GRAMMAR_ERROR = new Response(10005, "参数语法错误");
        Response INPUT_COERCION = new Response(10006, "参数越界");
        Response ILLEGAL = new Response(10007, "非法参数");
    }

    /**
     * 用户&认证错误 20001~29999
     */
    public interface AUTHENTICATION {
        Response ERROR = new Response(20500, "认证异常");
        Response UNSUPPORTED_GRANT_TYPE_EXCEPTION = new Response(20501, "不支持该认证类型");
        Response REFRESH_TOKEN_EXPIRED = new Response(20502, "刷新令牌已过期，请重新登录");
        Response SCOPE_EXCEPTION = new Response(20503, "不是有效的scope值");
        Response REFRESH_TOKEN_INVALID = new Response(20504, "refresh token无效");
        Response FAIL = new Response(20505, "认证失败");
        Response REDIRECT_MISMATCH_EXCEPTION = new Response(20506, "redirect_uri值不正确");
        Response BAD_CLIENT_CREDENTIALS_EXCEPTION = new Response(20507, "client值不合法");
        Response UNSUPPORTED_RESPONSE_TYPE_EXCEPTION = new Response(20508, "不是合法的response_type值");
        Response INVALID_AUTHORIZATION_CODE = new Response(20509, "授权码不合法");
        Response UNAUTHORIZED_GRANT_TYPE = new Response(20510, "未经授权的认证类型");

        Response NOT_LOGGED_IN = new Response(20001, "用户未登陆，访问的路径需要验证，请登陆");
        Response BAD_CREDENTIALS = new Response(20002, "用户名或密码错误");
        Response ACCOUNT_FORBIDDEN = new Response(20003, "帐号已被禁用");
        Response NOT_EXIST = new Response(20004, "用户不存在");
        Response HAS_EXISTED = new Response(20005, "用户已存在");
        Response ACCOUNT_LOCKED = new Response(20006, "帐号已被锁定");
        Response LOGIN_ERROR = new Response(20007, "登陆失败");
        Response LOGOUT_ERROR = new Response(20008, "登出异常");
        Response VALIDATE_CODE_EXCEPTION = new Response(20009, "验证码校验异常");
        Response BAD_PHONE_SMS_CREDENTIALS = new Response(20010, "手机号或短信验证码错误");
    }

    /**
     * 接口异常 30001~39999
     */
    public interface INTERFACE {
        Response VALIDATE_CODE_UNOPEN = new Response(30001, "验证码功能未开放");
    }

    /**
     * 客户端异常 40001~49999
     */
    public interface CLIENT {
        Response UN_AUTHORIZED = new Response(40401, "Token无效");
        Response ACCESS_DENIED = new Response(40403, "没有权限访问该资源");
        Response DENIED_GATEWAY = new Response(40403, "请通过网关访问该资源");
        Response ACCESS_SERVICE_DENIED = new Response(40403, "目标服务拒绝请求");
        Response DENIED_URI = new Response(40403, "该URI不允许外部访问");
        Response NOT_FOND = new Response(40404, "未找到该资源");
        Response NOT_ACCEPTABLE = new Response(40406, "黑名单限制，禁止访问");
        Response TOO_MANY_REQUESTS = new Response(40429, "访问频率超限，请稍后再试");
    }

    /**
     * 服务端异常 50001~59999
     */
    public static interface SERVER {
        Response INTERNAL_EXCEPTION = new Response(50000, "系统内部异常");
        Response ERROR = new Response(50001, "系统错误");
        Response TIMEOUT = new Response(50002, "服务超时或不可用");
        Response GATEWAY_FORWARD_EXCEPTION = new Response(50003, "网关转发异常");
        Response REQUEST_EXCEPTION = new Response(50004, "服务请求异常");
        Response GATEWAY_FORWARD_UNABLE_TO_FIND_INSTANCE = new Response(50005, "网关无法找到目标服务实例");
        Response RATE_LIMIT_EXCEPTION = new Response(50006, "服务限流");
        Response UNKNOWN_ERROR = new Response(50999, "系统未知异常");
    }

    private final Integer code;
    private final String message;
    private Object data;
    private String description = "";

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    /**
     * @param cls data对象的class对象
     * @return data的实体对象
     */
    @JsonIgnore
    public <T> T getDataEntity(Class<T> cls) {
        if (this.data.getClass().equals(cls)) {
            return cls.cast(this.data);
        }
        return null;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public static Response success() {
        return SUCCESS.SUCCESS;
    }

    public static Response success(Object data) {
        return SUCCESS.SUCCESS.setData(data);
    }

    public boolean isSuccess() {
        return SUCCESS.SUCCESS.getCode().equals(this.getCode());
    }

    public String getDescription() {
        return description;
    }

    public Response setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 直接响应
     */
    public void response(String uri, ServletResponse response) {
        response(uri, response, HttpServletResponse.SC_OK);
    }

    /**
     * 直接响应
     *
     * @param response 响应体
     * @param status   HttpServletResponse
     */
    public void response(String uri, ServletResponse response, int status) {
        if (StringUtils.isEmpty(uri)) {
            log.info("At [{}] Response直接响应：{}", StackTraceUtil.callLocation(), this.getMessage());
        } else {
            log.info("{} ==> At [{}] Response直接响应：{}", uri, StackTraceUtil.callLocation(), this.getMessage());
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out.write(JsonUtil.toJson(this).getBytes());
        } catch (Exception e) {
            log.error("【Response直接响应异常】", e);
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.code);
        map.put("message", this.message);
        if (this.data != null) {
            map.put("data", this.data);
        }
        if (StringUtils.isNoneEmpty(this.description)) {
            map.put("description", this.description);
        }
        return map;
    }

}
