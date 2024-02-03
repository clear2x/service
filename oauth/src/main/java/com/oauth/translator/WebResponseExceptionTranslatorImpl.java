package com.oauth.translator;

import com.core.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 异常翻译
 *
 * @author yuangy
 * @create 2020/7/1 10:11
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class WebResponseExceptionTranslatorImpl implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<?> translate(Exception e) {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

        log.debug(Response.AUTHENTICATION.FAIL.getMessage(), e);
        log.info(Response.AUTHENTICATION.FAIL.getMessage());

        if (e instanceof UnsupportedGrantTypeException) {
            return status.body(Response.AUTHENTICATION.UNSUPPORTED_GRANT_TYPE_EXCEPTION);
        }
        if (e instanceof InvalidClientException && StringUtils.containsIgnoreCase(e.getMessage(), "Unauthorized grant type")) {
            return status.body(Response.AUTHENTICATION.UNAUTHORIZED_GRANT_TYPE);
        }
        if (e instanceof InvalidTokenException
                && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
            return status.body(Response.AUTHENTICATION.REFRESH_TOKEN_EXPIRED);
        }
        if (e instanceof InvalidScopeException) {
            return status.body(Response.AUTHENTICATION.SCOPE_EXCEPTION);
        }
        if (e instanceof RedirectMismatchException) {
            return status.body(Response.AUTHENTICATION.REDIRECT_MISMATCH_EXCEPTION);
        }
        if (e instanceof BadClientCredentialsException) {
            return status.body(Response.AUTHENTICATION.BAD_CLIENT_CREDENTIALS_EXCEPTION);
        }
        if (e instanceof UnsupportedResponseTypeException) {
            String code = StringUtils.substringBetween(e.getMessage(), "[", "]");
            return status.body(Response.AUTHENTICATION.UNSUPPORTED_RESPONSE_TYPE_EXCEPTION.setDescription(code + Response.AUTHENTICATION.UNSUPPORTED_RESPONSE_TYPE_EXCEPTION.getMessage()));
        }
        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                return status.body(Response.AUTHENTICATION.REFRESH_TOKEN_INVALID);
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid authorization code")) {
                String code = StringUtils.substringAfterLast(e.getMessage(), ": ");
                return status.body(Response.AUTHENTICATION.INVALID_AUTHORIZATION_CODE.setDescription(code + "不合法"));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                return status.body(Response.AUTHENTICATION.ACCOUNT_LOCKED);
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Bad PhoneSms credentials")) {
                return status.body(Response.AUTHENTICATION.BAD_PHONE_SMS_CREDENTIALS);
            }
            return status.body(Response.AUTHENTICATION.BAD_CREDENTIALS);
        }
        return status.body(Response.AUTHENTICATION.FAIL);
    }
}
