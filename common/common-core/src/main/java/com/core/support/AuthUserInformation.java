package com.core.support;

/**
 * 当前认证用户的信息获取接口
 *
 * @author yuangy
 * @create 2020-07-10 9:47
 */
public interface AuthUserInformation {

    /**
     * 获取当前用户的ID
     *
     * @return 当前用户ID
     */
    Long getCurrentUserId();

    /**
     * 获取当前用户的用户名
     *
     * @return 当前用户名
     */
    String getCurrentUserName();


}
