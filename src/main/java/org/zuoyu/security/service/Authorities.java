package org.zuoyu.security.service;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.zuoyu.security.model.AuthorityVO;
import org.zuoyu.security.model.ContentVO;
import org.zuoyu.security.model.MenuVO;
import org.zuoyu.utils.JsonUtil;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description : 权限集
 * @date : 2019-12-10 16:50
 **/
public class Authorities {

    private static volatile Authorities authorities;
    private final List<AuthorityVO> authorityVOS;

    @SneakyThrows
    private Authorities() {
        InputStream inputStream = Authorities.class.getClassLoader().getResourceAsStream("static/authorities.json");
        CollectionType collectionType = JsonUtil.getInstance().getTypeFactory().constructCollectionType(List.class, AuthorityVO.class);
        Assert.notNull(inputStream, "inputStream is null");
        authorityVOS = JsonUtil.getInstance().readValue(inputStream, collectionType);
    }

    public static Authorities getInstance() {
        if (Objects.isNull(authorities)) {
            synchronized (Authorities.class) {
                if (Objects.isNull(authorities)) {
                    authorities = new Authorities();
                }
            }
        }
        return authorities;
    }

    public List<AuthorityVO> authorityList() {
        return this.authorityVOS;
    }

    private List<ContentVO> contentBuilder(Map<String, List<AuthorityVO>> contentGroup) {
        //        根据分组组装对象
        List<ContentVO> contents = contentGroup.keySet().stream().map(key -> {
            List<AuthorityVO> authorities = contentGroup.get(key);
            ContentVO contentVO = new ContentVO(key, authorities.get(0).getIcon());
//            下面这段代码是处理二级菜单的
            List<MenuVO> menuList = new ArrayList<>(authorities.size());
            menuList.addAll(authorities.parallelStream().map(authority ->
                    new MenuVO(authority.getName(), authority.getUrl()))
                    .collect(Collectors.toList()));
            contentVO.setMenuList(menuList);
////            下面这段代码是处理三级菜单的
//            List<ContentVO> cons = authorities.stream().filter(authorityVO ->
//                    !CollectionUtils.isEmpty(authorityVO.getMenus())).map(authorityVO -> {
//                ContentVO content = new ContentVO(authorityVO.getName());
//                List<MenuVO> menus = new ArrayList<>(authorityVO.getMenus().size());
//                menus.addAll(authorityVO.getMenus().parallelStream().map(menu ->
//                        new MenuVO(menu.getName(), menu.getUrl())).collect(Collectors.toList()));
//                content.setMenuList(menus);
//                return content;
//            }).collect(Collectors.toList());
//            contentVO.setContentList(cons);
            return contentVO;
//            合一起
        }).collect(Collectors.toList());
        Collections.reverse(contents);
        return contents;
    }

    public List<ContentVO> getContentsByAuthorityIds(List<String> authorities) {
        Assert.notEmpty(authorities, "The user does not have permissions");
        Map<String, List<AuthorityVO>> contentGroup = this.authorityVOS.parallelStream().filter(authorityVO ->
                authorities.contains(authorityVO.getId())).collect(Collectors.groupingBy(AuthorityVO::getTitle));
        return contentBuilder(contentGroup);
    }

    public List<ContentVO> getContentsByAuthorities(Collection<? extends GrantedAuthority> authorities) {
        List<String> authorityIds = authorities.parallelStream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return getContentsByAuthorityIds(authorityIds);
    }
}
