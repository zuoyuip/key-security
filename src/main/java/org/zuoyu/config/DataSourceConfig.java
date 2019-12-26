package org.zuoyu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description : 数据源配置
 * @date : 2019-12-04 17:01
 **/
@MapperScan(basePackages = {
        "com.supergenius.admin.capital.mapper",
        "com.supergenius.admin.management.mapper",
        "com.supergenius.admin.user.mapper"})
public class DataSourceConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true);
        return paginationInterceptor;
    }
}
