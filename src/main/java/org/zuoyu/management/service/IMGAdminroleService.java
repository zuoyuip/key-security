package org.zuoyu.management.service;

import org.zuoyu.management.model.MGAdminroleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-12-04
 */
public interface IMGAdminroleService extends IService<MGAdminroleDO> {

    /**
     * 根据用户uid查询权限
     * @param adminId -
     * @return -
     */
    List<String> getAuthoritiesByAdminUid(String adminId);
}
