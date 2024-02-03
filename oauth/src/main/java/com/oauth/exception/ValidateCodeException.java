package com.oauth.exception;

import com.core.exception.BaseException;

/**
 * 验证码类型异常
 *
 * @author yuangy
 * @create 2020/6/30 16:17
 */
public class ValidateCodeException extends BaseException {

    public ValidateCodeException(String message) {
        super(message);
    }

}
