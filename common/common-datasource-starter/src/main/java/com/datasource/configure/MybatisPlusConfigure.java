package com.datasource.configure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bean.mysql.base.IsDeletedField;
import com.core.constant.DataBaseConstant;
import com.core.support.AuthUserInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yuangy
 * @create 2020/7/17 14:30
 */
@Slf4j
@EnableTransactionManagement
@RequiredArgsConstructor
public class MybatisPlusConfigure implements MetaObjectHandler {

    private final AuthUserInformation authUserInformation;

    /**
     * 插入时自动填充配置
     *
     * @param metaObject 操作对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("新增操作进行填充...");

        // 创建者
        Object createBy = getFieldValByName(DataBaseConstant.CREATE_BY_FIELD, metaObject);
        if (Objects.nonNull(authUserInformation) && Objects.isNull(createBy)) {
            String currentUserName;
            try {
                currentUserName = authUserInformation.getCurrentUserName();
            } catch (Exception e) {
                currentUserName = "system";
            }
            setFieldValByName(DataBaseConstant.CREATE_BY_FIELD, currentUserName, metaObject);
        }
        // 创建时间
        Object createTime = getFieldValByName(DataBaseConstant.CREATE_TIME_FIELD, metaObject);
        if (Objects.isNull(createTime)) {
            setFieldValByName(DataBaseConstant.CREATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
        // 是否已删除
        Object isDeleted = getFieldValByName(DataBaseConstant.IS_DELETED_FIELD, metaObject);
        if (Objects.isNull(isDeleted)) {
            setFieldValByName(DataBaseConstant.IS_DELETED_FIELD, IsDeletedField.NOT_DELETE, metaObject);
        }
    }

    /**
     * 更新时自动填充配置
     *
     * @param metaObject 操作对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("更新操作进行填充...");

        // 更新者
        Object updateBy = getFieldValByName(DataBaseConstant.UPDATE_BY_FIELD, metaObject);
        if (Objects.nonNull(authUserInformation) && Objects.isNull(updateBy)) {
            String currentUserName;
            try {
                currentUserName = authUserInformation.getCurrentUserName();
            } catch (Exception e) {
                currentUserName = "system";
            }
            setFieldValByName(DataBaseConstant.UPDATE_BY_FIELD, currentUserName, metaObject);
        }
        // 更新时间
        Object updateTime = getFieldValByName(DataBaseConstant.UPDATE_TIME_FIELD, metaObject);
        if (Objects.isNull(updateTime)) {
            setFieldValByName(DataBaseConstant.UPDATE_TIME_FIELD, LocalDateTime.now(), metaObject);
        }
    }
}
