package com.security.service;

import com.core.support.AuthUserInformation;
import com.security.util.AuthUserUtil;

import java.util.Objects;

/**
 * @author yuangy
 * @create 2020-07-10 10:01
 */
public class AuthUserInformationService implements AuthUserInformation {
    @Override
    public Long getCurrentUserId() {
        return Objects.requireNonNull(AuthUserUtil.getCurrentUser()).getUserId();
    }

    @Override
    public String getCurrentUserName() {
        return AuthUserUtil.getCurrentUsername();
    }
}
