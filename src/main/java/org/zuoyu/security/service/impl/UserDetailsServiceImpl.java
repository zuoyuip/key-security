package org.zuoyu.security.service.impl;

import com.google.common.base.Strings;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zuoyu.exception.CustomException;
import org.zuoyu.security.service.ITokenService;
import org.zuoyu.utils.JsonUtil;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;
import java.util.Optional;

/**
 * @author : zuoyu
 * @description : 认证实现
 * @date : 2019-11-26 09:53
 **/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RedisTemplate<String, Object> redisTemplate;

    public UserDetailsServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
        ITokenService.User user = getToken(key);
        return null;
    }

    private ITokenService.User getToken(String key) {
        if (Strings.isNullOrEmpty(key)) {
            throw new CustomException("The key is null");
        }
        boolean hasKey = Optional.ofNullable(redisTemplate.hasKey(key)).orElseThrow(NullPointerException::new);
        if (!hasKey) {
            throw new CustomException("The key doesn't exist");
        }
        Object token = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(token)) {
            throw new CustomException("The token is null");
        }
        redisTemplate.delete(key);
        return JsonUtil.getInstance().convertValue(token, ITokenService.User.class);
    }

    @SneakyThrows
    private void verifyToken(ITokenService.User user, MGAdminDO adminDO) {
        //算法
        final String algorithm = "AES/ECB/PKCS5Padding";
        byte[] encryptBytes = new BASE64Decoder().decodeBuffer(user.getPassWord());
        KeyGenerator.getInstance("AES").init(128);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SecurityConstants.ASE_KEY.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        String passWord = new String(decryptBytes);
        PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("MD5");
        if (!passwordEncoder.matches(passWord, adminDO.getPwd())) {
            throw new InternalAuthenticationServiceException("verification failed");
        }
    }
}
