package com.security.service;

import com.core.http.Response;
import com.core.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 自定义tokenService
 *
 * @author yuangy
 * @create 2020-07-06 17:42
 * @see UserInfoTokenServices
 */
public class CustomUserInfoTokenServices implements ResourceServerTokenServices {
    protected final Log logger = LogFactory.getLog(getClass());

    private final String userInfoEndpointUrl;

    private String clientId;

    private final OAuth2RestTemplate restTemplate;

    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

    private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, OAuth2RestTemplate restTemplate) {
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.restTemplate = restTemplate;
    }

    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
        this.authoritiesExtractor = authoritiesExtractor;
    }

    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
        this.principalExtractor = principalExtractor;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {

        Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
        if (map.containsKey("error")) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("userinfo returned error: " + map.get("error"));
            }
            throw new InvalidTokenException(accessToken);
        }

        return extractAuthentication(map);
    }

    @SuppressWarnings("unchecked")
    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = getPrincipal(map);
        List<GrantedAuthority> authorities = this.authoritiesExtractor
                .extractAuthorities(map);

        LinkedHashMap<String, Object> oauth2Request = (LinkedHashMap<String, Object>) map.get("oauth2Request");
        LinkedHashMap<String, String> requestParameters = (LinkedHashMap<String, String>) oauth2Request.get("requestParameters");
        this.clientId = (String) oauth2Request.get("clientId");
        Boolean approved = (Boolean) oauth2Request.get("approved");
        List<String> scope = (ArrayList<String>) oauth2Request.get("scope");
        List<String> resourceIds = (ArrayList<String>) oauth2Request.get("resourceIds");
        String redirectUri = (String) oauth2Request.get("redirectUri");
        List<String> responseTypes = (ArrayList<String>) oauth2Request.get("responseTypes");
        Boolean authenticated = (Boolean) map.get("authenticated");

        if (approved == null) {
            approved = true;
        }
        if (authenticated == null) {
            authenticated = false;
        }

        OAuth2Request request = new OAuth2Request(requestParameters, this.clientId, null, approved, new HashSet<>(scope),
                new HashSet<>(resourceIds), redirectUri, new HashSet<>(responseTypes), null);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal, "N/A", authorities);
        token.setDetails(map);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request, token);
        oAuth2Authentication.setAuthenticated(authenticated);

        return oAuth2Authentication;
    }

    /**
     * Return the principal that should be used for the token. The default implementation
     * delegates to the {@link PrincipalExtractor}.
     *
     * @param map the source map
     * @return the principal or {@literal "unknown"}
     */
    protected Object getPrincipal(Map<String, Object> map) {
        Object principal = this.principalExtractor.extractPrincipal(map);
        return (principal == null ? "unknown" : principal);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    /**
     * 主要是重写这个方法
     */
    @SuppressWarnings({"unchecked"})
    private Map<String, Object> getMap(String path, String accessToken) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Getting user info from: " + path);
        }
        try {
            OAuth2RestOperations restTemplate = this.restTemplate;
            if (restTemplate == null) {
                BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
                resource.setClientId(this.clientId);
                restTemplate = new OAuth2RestTemplate(resource);
            }
            OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
                    .getAccessToken();
            if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
                        accessToken);
                String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;
                token.setTokenType(tokenType);
                restTemplate.getOAuth2ClientContext().setAccessToken(token);
            }
            // 加上对Response返回信息的判断
            Map<String, Object> body = restTemplate.getForEntity(path, Map.class).getBody();
            if (body != null) {
                if (body.containsKey("code") && body.get("code").equals(Response.SUCCESS.SUCCESS.getCode()) && body.containsKey("data")) {
                    body = JsonUtil.parse(JsonUtil.toJson(body.get("data")), Map.class);
                } else {
                    body.put("error", "获取用户信息失败");
                }
            } else {
                body = new HashMap<>();
                body.put("error", "调用服务失败");
            }
            return body;
        } catch (Exception ex) {
            this.logger.warn("Could not fetch user details: " + ex.getClass() + ", "
                    + ex.getMessage());
            return Collections.singletonMap("error",
                    "Could not fetch user details");
        }
    }

}
