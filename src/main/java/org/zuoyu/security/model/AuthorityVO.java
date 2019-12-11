package org.zuoyu.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : zuoyu
 * @description : 权限
 * @date : 2019-12-10 16:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AuthorityVO对象", description = "权限")
public class AuthorityVO {

    @ApiModelProperty(value = "权限ID")
    private String id;

    @ApiModelProperty(value = "菜单目录")
    private String title;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "目录图标")
    private String icon;
}
