package org.zuoyu.security;

import org.zuoyu.security.config.KeyCodeAuthenticationSecurityConfig;
import org.zuoyu.security.constants.SecurityConstants;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zuoyu
 * @description : 权限配置
 * @date : 2019-11-21 11:09
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final KeyCodeAuthenticationSecurityConfig keyCodeAuthenticationSecurityConfig;

    public SecurityConfig(KeyCodeAuthenticationSecurityConfig keyCodeAuthenticationSecurityConfig) {
        this.keyCodeAuthenticationSecurityConfig = keyCodeAuthenticationSecurityConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                放行资源跨域
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
//                关闭跨站请求伪造保护
                .csrf(AbstractHttpConfigurer::disable)
//                关闭访问请求的缓存
                .requestCache(RequestCacheConfigurer::disable)
//                默认任何资源可匿名访问
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry.requestMatchers(CorsUtils::isPreFlightRequest)
                                .permitAll()
                                .antMatchers().authenticated()
                                .anyRequest().permitAll())
                .apply(keyCodeAuthenticationSecurityConfig)
                .and()

//                “记住我”配置
                .rememberMe(AbstractHttpConfigurer::disable)
//                注销配置
                .logout(logoutConfigurer ->
                        logoutConfigurer.logoutUrl(SecurityConstants.USER_LOGOUT_URL)
                                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                                .clearAuthentication(true)
                                .permitAll())
//                各种情况拦截器配置
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler(new AccessDeniedHandlerImpl())
                                .authenticationEntryPoint(new AuthenticationEntryPointImpl()));
    }

    @Bean("passwordEncoder")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 配置跨域
     */
    @Bean("corsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    /**
     * 用来解决认证过的用户访问无权限资源时的异常.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class AccessDeniedHandlerImpl implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, AccessDeniedException e)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "对不起，您没有访问权限");
        }
    }

    /**
     * 用来解决匿名用户访问无权限资源时的异常.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, AuthenticationException e)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "您还没有登录，请先登录");
        }
    }

    /**
     * 注销成功的实现.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, Authentication authentication)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter responseWriter = httpServletResponse.getWriter();
            responseWriter.write("{\"message\":\"注销成功\"}");
            responseWriter.flush();
            responseWriter.close();
        }
    }
}
