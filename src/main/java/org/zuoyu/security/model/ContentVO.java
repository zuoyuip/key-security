package org.zuoyu.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : zuoyu
 * @description : 目录
 * @date : 2019-12-10 16:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Content对象", description="目录")
public class ContentVO {

    @ApiModelProperty(value = "目录名称")
    private String name;

    @ApiModelProperty(value = "目录图标")
    private String icon;

    @ApiModelProperty(value = "菜单列表")
    private List<MenuVO> menuList;

    public ContentVO(String name) {
        this.name = name;
    }

    public ContentVO(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }


}
