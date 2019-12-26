package org.zuoyu.security.provider;

import org.zuoyu.security.token.KeyCodeAuthenticationToken;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description : 基于key的校验认证提供
 * @date : 2019-12-09 11:43
 **/
@Data
public class KeyCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 认证源
     */
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeyCodeAuthenticationToken token = (KeyCodeAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) token.getPrincipal());
        if (Objects.isNull(userDetails)){
            throw new InternalAuthenticationServiceException("The user information could not be obtained");
        }
        KeyCodeAuthenticationToken keyCodeAuthenticationToken = new KeyCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        keyCodeAuthenticationToken.setDetails(token.getDetails());
        return keyCodeAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return KeyCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
