package org.zuoyu.security.handler;

import com.baomidou.mybatisplus.extension.api.R;
import org.zuoyu.security.model.ContentVO;
import org.zuoyu.security.service.Authorities;
import org.zuoyu.utils.JsonUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author : zuoyu
 * @description : 登陆成功行为
 * @date : 2019-11-21 11:19
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<ContentVO> contents = Authorities.getInstance().getContentsByAuthorities(authorities);
        R<List<ContentVO>> r = R.ok(contents).setMsg(authentication.getName());
        byte[] bytes = JsonUtil.getInstance().writeValueAsBytes(r);
        servletOutputStream.write(bytes);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}

