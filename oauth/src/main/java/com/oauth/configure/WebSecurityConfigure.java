package com.oauth.configure;

import com.core.constant.EndpointConstant;
import com.oauth.filter.ValidateCodeFilter;
import com.oauth.properties.OauthProperties;
import com.oauth.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

@Order(2) // 使优先级高于ResourceServerConfigure的优先级(3)，数字越小，优先级越高
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailService;
    private final ValidateCodeFilter validateCodeFilter;
    private final PasswordEncoder passwordEncoder;
    private final OauthProperties oauthProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 系统默认的密码模式的DaoAuthenticationProvider也是受这个管理
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 对 /oauth/**生效
                .requestMatchers().antMatchers(EndpointConstant.OAUTH_ALL)
                .and()
                // 对 /oauth/**请求需要认证
                .authorizeRequests().antMatchers(EndpointConstant.OAUTH_ALL).authenticated()
                // 剩下的URL全部通过
                .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        // 给默认的认证方式添加自己的Provider
        if (oauthProperties.getEnablePasswordSecureTransport()) {
            auth.authenticationProvider(customAuthenticationProvider());
        }
    }

    private CustomAuthenticationProvider customAuthenticationProvider() {
        Assert.notNull(oauthProperties.getRsaPrivateKey(), "私钥不能为空");
        Assert.notNull(oauthProperties.getRsaPublicKey(), "公钥不能为空");
        return new CustomAuthenticationProvider(UsernamePasswordAuthenticationToken.class, userDetailService, passwordEncoder, oauthProperties.getRsaPrivateKey());
    }

}
