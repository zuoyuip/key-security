package org.zuoyu.management.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zuoyu.management.model.MGAdminauthorityDO;
import org.springframework.stereotype.Repository;

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
public interface MGAdminauthorityDao extends BaseMapper<MGAdminauthorityDO> {

}
