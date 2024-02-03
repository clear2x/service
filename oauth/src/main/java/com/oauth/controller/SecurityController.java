package com.oauth.controller;

import com.core.http.Response;
import com.oauth.exception.ValidateCodeException;
import com.oauth.properties.OauthProperties;
import com.oauth.service.ValidateCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final ConsumerTokenServices consumerTokenServices;
    private final ValidateCodeService validateCodeService;
    private final OauthProperties oauthProperties;

    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }

    @GetMapping("user")
    public Authentication currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @ResponseBody
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        if (oauthProperties.getEnableCode()) {
            validateCodeService.create(request, response);
        } else {
            Response.INTERFACE.VALIDATE_CODE_UNOPEN.setDescription(Response.INTERFACE.VALIDATE_CODE_UNOPEN.getMessage()).response(request.getRequestURL().toString(), response);
        }
    }

    @DeleteMapping("signout")
    public Response signout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = RegExUtils.replaceAll(authorization, "(b|B)earer\\s+", "");
        if (!consumerTokenServices.revokeToken(token)) {
            return Response.AUTHENTICATION.LOGOUT_ERROR;
        }
        return Response.SUCCESS.LOGOUT_SUCCESS;
    }

}
