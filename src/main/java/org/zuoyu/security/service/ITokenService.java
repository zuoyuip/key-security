package org.zuoyu.security.service;

import lombok.Data;

/**
 * @author : zuoyu
 * @description : 身份
 * @date : 2019-12-09 15:04
 **/
public interface ITokenService {

    int EXPIRE_TIME = 60;

    /**
     * 存储token（一分钟）
     *
     * @param key  - key
     * @param user - token
     */
    void depositToken(String key, User user);

    /**
     * 暂时存放User
     */
    @Data
    class User {
        private String userName;
        private String passWord;
    }
}
