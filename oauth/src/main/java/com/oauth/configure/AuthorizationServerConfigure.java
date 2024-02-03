package com.oauth.configure;

import com.core.constant.ServiceConstant;
import com.oauth.granter.PhoneSmsTokenGranter;
import com.oauth.manager.UserManager;
import com.oauth.properties.OauthProperties;
import com.oauth.provider.PhoneSmsAuthenticationProvider;
import com.oauth.service.impl.CustomTokenServices;
import com.oauth.service.impl.RedisClientDetailsService;
import com.oauth.service.impl.UserDetailByPhoneServiceImpl;
import com.oauth.strategy.JacksonRedisTokenStoreSerializationStrategy;
import com.oauth.token.PhoneAuthenticationToken;
import com.oauth.translator.WebResponseExceptionTranslatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author yuangy
 * @create 2020-06-30 18:22
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    /**
     * @see WebSecurityConfigure 的authenticationManagerBean()
     */
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final WebResponseExceptionTranslatorImpl webResponseExceptionTranslator;
    private final OauthProperties properties;
    private final RedisClientDetailsService redisClientDetailsService;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserManager userManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
        redisClientDetailsService.loadAllClientToCache();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService)
                .exceptionTranslator(webResponseExceptionTranslator)
                .tokenGranter(tokenGranter(endpoints));
        // 授权码模式下，code存储
        // endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
        // endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
        if (properties.getEnableJwt()) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter());
        }
    }

    @SuppressWarnings("unchecked")
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        // 这里一定要设置 ClientDetailsService
        endpoints.setClientDetailsService(redisClientDetailsService);
        List<TokenGranter> tokenGranters = null;
        try {
            Class<AuthorizationServerEndpointsConfigurer> authorizationServerEndpointsConfigurerClass = AuthorizationServerEndpointsConfigurer.class;
            Method getDefaultTokenGranters = authorizationServerEndpointsConfigurerClass.getDeclaredMethod("getDefaultTokenGranters");
            getDefaultTokenGranters.setAccessible(true);
            tokenGranters = (List<TokenGranter>) getDefaultTokenGranters.invoke(endpoints);
            // 自定义认证模式
            // 添加手机短信认证模式
            tokenGranters.add(new PhoneSmsTokenGranter(new ProviderManager(new PhoneSmsAuthenticationProvider(PhoneAuthenticationToken.class, new UserDetailByPhoneServiceImpl(userManager))), tokenServices(), redisClientDetailsService, endpoints.getOAuth2RequestFactory()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TokenGranter> finalTokenGranters = tokenGranters;
        return new TokenGranter() {
            private CompositeTokenGranter delegate;
            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(finalTokenGranters);
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    /*=================================== Bean =============================================*/

    @Primary
    @Bean
    public CustomTokenServices tokenServices() {
        CustomTokenServices tokenServices = new CustomTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(redisClientDetailsService);
        // 提供给刷新token使用，为了再次认证，参考：CustomTokenServices.refreshAccessToken方法
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailService));
        tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        // token增强
        if (properties.getEnableJwt()) {
            tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        }
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        if (properties.getEnableJwt()) {
            return new JwtTokenStore(jwtAccessTokenConverter());
        } else {
            RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
            // 自定义序列化方式
            redisTokenStore.setSerializationStrategy(new JacksonRedisTokenStoreSerializationStrategy());
            // 解决每次生成的 token都一样的问题，注释掉保证：生成的key相同，以此实现登录互踢
//            redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
            return redisTokenStore;
        }
    }

    /*=================================== JWT有关配置 =============================================*/

    @Bean
    @ConditionalOnProperty(value = ServiceConstant.PROPERTIES_PREFIX + "oauth.enable-jwt", havingValue = "true", matchIfMissing = true)
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        accessTokenConverter.setSigningKey(properties.getJwtAccessKey());
        return accessTokenConverter;
    }

}
