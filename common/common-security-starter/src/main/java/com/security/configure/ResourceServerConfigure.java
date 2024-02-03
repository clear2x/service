package com.security.configure;

import com.core.constant.ServiceConstant;
import com.security.handler.ServiceAccessDeniedHandler;
import com.security.handler.ServiceAuthExceptionEntryPoint;
import com.security.properties.SecurityProperties;
import com.security.service.CustomUserInfoTokenServices;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.util.Assert;

@EnableResourceServer
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
@RequiredArgsConstructor
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

    private final SecurityProperties securityProperties;
    private final ServiceAuthExceptionEntryPoint serviceAuthExceptionEntryPoint;
    private final ServiceAccessDeniedHandler accessDeniedHandler;
    private final ResourceServerProperties resourceServerProperties;

    @Autowired(required = false)
    private UserInfoRestTemplateFactory userInfoRestTemplateFactory;

    @Value("${" + ServiceConstant.PROPERTIES_PREFIX + "doc.enable:false}")
    private boolean enableDoc;

    /**
     * 如果开启了文档接口，则默认将以下URI设置免认证的
     */
    private static final String[] DEFAULT_DOC_ANON_URIS = {
            "/v2/api-docs/**",
            "/v2/api-docs-ext/**",
            "/swagger-resources/**",
            "/doc.html",
            "/service-worker.js",
            "/precache-manifest.*.js",
            "/webjars/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getAnonUris(), ",");
        if (ArrayUtils.isEmpty(anonUrls)) {
            anonUrls = new String[]{};
        }
        if (enableDoc) {
            anonUrls = ArrayUtils.addAll(anonUrls, DEFAULT_DOC_ANON_URIS);
        }

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticated = http
                .requestMatchers()
                .antMatchers(securityProperties.getAuthUri())
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers(securityProperties.getAuthUri()).authenticated();
        if (StringUtils.isNotEmpty(securityProperties.getScope())) {
            authenticated.antMatchers(securityProperties.getAuthUri()).access("#oauth2.hasAnyScope('" + securityProperties.getScope() + "')");
        }
        http.csrf().disable().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(serviceAuthExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        if (!securityProperties.getIsAuthCenter()) {
            Assert.notNull(userInfoRestTemplateFactory, "userInfoRestTemplateFactory为null");
            // 自定义tokenService
            OAuth2RestTemplate userInfoRestTemplate = userInfoRestTemplateFactory.getUserInfoRestTemplate();
            CustomUserInfoTokenServices customCheckTokenServices = new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri(), userInfoRestTemplate);
            customCheckTokenServices.setClientId(resourceServerProperties.getClientId());
            resources.tokenServices(customCheckTokenServices);
        }
    }

}
