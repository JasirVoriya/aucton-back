package cn.voriya.framework.mybatis.config;

import cn.voriya.framework.security.AuthUser;
import cn.voriya.framework.security.context.UserContext;
import cn.voriya.framework.utils.SnowFlakeUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        AuthUser authUser = UserContext.getCurrentUser();
        this.setFieldValByName("createBy", authUser != null ? authUser.getUsername() : "SYSTEM", metaObject);
        this.setFieldValByName("updateBy", authUser != null ? authUser.getUsername() : "SYSTEM", metaObject);
        //有创建时间字段，切字段值为空
        if (metaObject.hasGetter("createTime")) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        //有更新时间字段
        if (metaObject.hasGetter("updateTime")) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        //有值，则写入
        if (metaObject.hasGetter("deleteFlag")) {
            this.setFieldValByName("deleteFlag", false, metaObject);
        }
        if (metaObject.hasGetter("id")) {
            //如果已经配置id，则不再写入
            if (metaObject.getValue("id") == null) {
                this.setFieldValByName("id", String.valueOf(SnowFlakeUtil.getId()), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser != null) {
            this.setFieldValByName("updateBy", authUser.getUsername(), metaObject);
        } else {
            this.setFieldValByName("updateBy", "SYSTEM", metaObject);
        }
        //有更新时间字段
        if (metaObject.hasGetter("updateTime")) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}