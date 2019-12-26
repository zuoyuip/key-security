package org.zuoyu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author : zuoyu
 * @project : proj-supergenius-vc-admin
 * @description : Mybatis-Plus的自动填充
 * @date : 2019-12-06 10:19
 **/
@Component
public class AutoFillHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createtime";
    private static final String U_ID = "uid";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.setInsertFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
        if (metaObject.hasSetter(U_ID)) {
            this.setInsertFieldValByName(U_ID, uuidGenerator(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    private String uuidGenerator() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
