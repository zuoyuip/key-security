package org.zuoyu.management.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zuoyu.management.model.MGAdminroleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-12-04
 */
@DS(value = "slave_management")
@Repository
public interface MGAdminroleDao extends BaseMapper<MGAdminroleDO> {

    /**
     * 根据用户uid查询权限
     * @param adminId -
     * @return -
     */
    List<String> getAuthoritiesByAdminUid(String adminId);
}
