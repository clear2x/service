package com.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 手机号或验证码错误
 *
 * @author yuangy
 * @create 2020-08-07 17:35
 */
public class CreateAuthenticationTokenException extends AuthenticationException {

    public CreateAuthenticationTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public CreateAuthenticationTokenException(String msg) {
        super(msg);
    }

}
