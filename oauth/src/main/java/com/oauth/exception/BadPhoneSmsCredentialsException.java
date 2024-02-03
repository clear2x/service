package com.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 手机号或验证码错误
 *
 * @author yuangy
 * @create 2020-08-07 17:35
 */
public class BadPhoneSmsCredentialsException extends AuthenticationException {

    public BadPhoneSmsCredentialsException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadPhoneSmsCredentialsException(String msg) {
        super(msg);
    }

}
