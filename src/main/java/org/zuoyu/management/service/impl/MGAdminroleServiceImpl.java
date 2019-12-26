package org.zuoyu.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.zuoyu.management.mapper.MGAdminroleDao;
import org.zuoyu.management.model.MGAdminroleDO;
import org.zuoyu.management.service.IMGAdminroleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-12-04
 */
@Service
public class MGAdminroleServiceImpl extends ServiceImpl<MGAdminroleDao, MGAdminroleDO> implements IMGAdminroleService {


    private final MGAdminroleDao adminroleDao;

    public MGAdminroleServiceImpl(MGAdminroleDao adminroleDao) {
        this.adminroleDao = adminroleDao;
    }

    @Override
    public List<String> getAuthoritiesByAdminUid(String adminId) {
        return adminroleDao.getAuthoritiesByAdminUid(adminId);
    }
}
