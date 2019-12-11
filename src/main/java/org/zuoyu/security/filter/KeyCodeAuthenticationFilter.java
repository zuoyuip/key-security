package org.zuoyu.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zuoyu.security.constants.SecurityConstants;
import org.zuoyu.security.token.KeyCodeAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author : zuoyu
 * @description : 基于key的认证过滤器
 * @date : 2019-12-09 11:02
 **/
public class KeyCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String HTTP_METHOD = "POST";
    private boolean postOnly = true;

    public KeyCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.USER_LOGIN_URL, HTTP_METHOD));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && (!HTTP_METHOD.equals(httpServletRequest.getMethod()))) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }
        String key = obtainKey(httpServletRequest);
        if (Objects.isNull(key)) {
            key = "";
        }
        KeyCodeAuthenticationToken token = new KeyCodeAuthenticationToken(key);
//       注入token
        setDetails(httpServletRequest, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    protected String obtainKey(HttpServletRequest request) {
        return request.getParameter(SecurityConstants.USER_LOGIN_KEY_PARAMETER);
    }

    protected void setDetails(HttpServletRequest request, KeyCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
