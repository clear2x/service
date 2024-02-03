package com.oauth.provider;

import com.oauth.exception.CreateAuthenticationTokenException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author yuangy
 * @create 2020-08-07 17:55
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider 参考
 */
@Slf4j
@Data
public abstract class AbstractUserDetailsAuthenticationProvider<T extends AbstractAuthenticationToken> implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    /* ====================================== 字段 =============================================*/

    /**
     * token class
     */
    private Class<T> tokenClass;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserCache userCache = new NullUserCache();
    private boolean forcePrincipalAsString = false;
    protected boolean hideUserNotFoundExceptions = true;
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private UserDetailsService userDetailsService;

    /* ====================================== 构造器 =============================================*/

    public AbstractUserDetailsAuthenticationProvider(Class<T> tokenClass, UserDetailsService userDetailsService) {
        this.tokenClass = tokenClass;
        this.userDetailsService = userDetailsService;
    }

    private AbstractUserDetailsAuthenticationProvider() {
    }

    /* ====================================== 抽象方法 =============================================*/

    /**
     * 校验逻辑
     *
     * @param userDetails    user information
     * @param authentication 凭证
     * @throws AuthenticationException 认证异常
     */
    protected abstract void additionalAuthenticationChecks(UserDetails userDetails, T authentication) throws AuthenticationException;

    /**
     * 查询用户
     *
     * @param param          查询参数
     * @param authentication 凭证
     * @return user information
     * @throws AuthenticationException 认证异常
     */
    protected UserDetails retrieveUser(String param, T authentication) throws AuthenticationException {
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(param);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /* ====================================== 认证 =============================================*/

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        Assert.isInstanceOf(tokenClass, authentication, () -> messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));

        // 校验
        String principal = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

        boolean cacheWasUsed = true;
        // 从缓存中获取用户信息
        UserDetails user = this.userCache.getUserFromCache(principal);

        T token = tokenClass.cast(authentication);

        if (user == null) {
            cacheWasUsed = false;
            try {
                user = retrieveUser(principal, token);
            } catch (Exception e) {

                log.debug("User '" + principal + "' not found");
                // 是否隐藏异常
                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                } else {
                    throw e;
                }
            }
            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }

        try {
            // 验证账户状态
            preAuthenticationChecks.check(user);
            // 校验逻辑
            additionalAuthenticationChecks(user, token);
        } catch (AuthenticationException exception) {
            if (cacheWasUsed) {
                cacheWasUsed = false;
                user = retrieveUser(principal, token);
                // 验证账户状态
                preAuthenticationChecks.check(user);
                // 校验逻辑
                additionalAuthenticationChecks(user, token);
            } else {
                throw exception;
            }
        }

        // 校验凭证（密码）是否过期
        postAuthenticationChecks.check(user);

        // 如果未缓存，则缓存
        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }

        Object principalToReturn = user;

        if (forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }

        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    /**
     * 创建成功的证书（token）
     *
     * @param principal      身份，如：用户名/手机号
     * @param authentication 证书
     * @param user           用户信息
     * @return 已认证的证书
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        try {
            Constructor<T> constructor = tokenClass.getConstructor(Object.class, Object.class, Collection.class);
            T token = constructor.newInstance(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(user.getAuthorities()));
            Method setDetails = tokenClass.getMethod("setDetails", Object.class);
            setDetails.invoke(token, authentication.getDetails());
            return token;
        } catch (Exception e) {
            throw new CreateAuthenticationTokenException("创建token失败", e);
        }
    }

    /* ====================================== ProviderManager用到，用来判断是否支持该token =============================================*/

    public boolean supports(Class<?> authentication) {
        return tokenClass.isAssignableFrom(authentication);
    }

    /* ====================================== Spring初始化Bean用到 =============================================*/

    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userCache, "A user cache must be set");
        Assert.notNull(this.messages, "A message source must be set");
        doAfterPropertiesSet();
    }

    protected void doAfterPropertiesSet() throws Exception {
    }

    /**
     * 信息国际化的接口，这里暂不处理
     *
     * @param messageSource
     */
    @SuppressWarnings("all")
    public void setMessageSource(MessageSource messageSource) {

    }

    /* ====================================== 私有 =============================================*/

    /**
     * 默认的账户状态检查：锁定，不可用，过期
     */
    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("User account is locked");
                throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            }
            if (!user.isEnabled()) {
                log.debug("User account is disabled");
                throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            }
            if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");
                throw new AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }

    /**
     * 凭证（密码）是否过期
     */
    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");
                throw new CredentialsExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }
}
