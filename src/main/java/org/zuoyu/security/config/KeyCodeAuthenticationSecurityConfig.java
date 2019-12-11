package org.zuoyu.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.zuoyu.security.filter.KeyCodeAuthenticationFilter;
import org.zuoyu.security.provider.KeyCodeAuthenticationProvider;

/**
 * @author : zuoyu
 * @description : 基于key认证的配置
 * @date : 2019-12-09 12:24
 **/
@Component
public class KeyCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public KeyCodeAuthenticationSecurityConfig(UserDetailsService userDetailsService, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        KeyCodeAuthenticationProvider provider = new KeyCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        KeyCodeAuthenticationFilter filter = new KeyCodeAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        builder.authenticationProvider(provider).addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
        super.configure(builder);
    }


}
