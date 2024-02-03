package com.oauth.provider;

import com.oauth.token.PhoneAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class PhoneSmsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider<PhoneAuthenticationToken> {

    public PhoneSmsAuthenticationProvider(Class<PhoneAuthenticationToken> tokenClass, UserDetailsService userDetailsService) {
        super(tokenClass, userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, PhoneAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad PhoneSms credentials"));
        }

        String verifyCode = authentication.getCredentials().toString();

        // 进行验证码的比较
        if (!"6666".equals(verifyCode)) {
            throw new BadCredentialsException(messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad PhoneSms credentials"));
        }

    }

}
