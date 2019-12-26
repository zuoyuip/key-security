package org.zuoyu.security.controller;

import org.zuoyu.security.service.ITokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description : 身份管理
 * @date : 2019-12-09 15:38
 **/
@RestController
@RequestMapping("/token")
public class TokenController {

    private final ITokenService iTokenService;

    public TokenController(ITokenService iTokenService) {
        this.iTokenService = iTokenService;
    }


    @PutMapping(path = "/{key}")
    public void deposit(@PathVariable String key, ITokenService.User user){
        iTokenService.depositToken(key, user);
    }

    @GetMapping
    public void testAuthentication(Authentication authentication){
        System.out.println(authentication.toString());
    }

}
