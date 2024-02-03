package com.bean.mysql.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公共字段
 *
 * @author yuangy
 * @create 2020-07-17 15:32
 */
@Data
public class IsDeletedField {

    public static final Integer NOT_DELETE = 0;
    public static final Integer DELETED = 1;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除（0=未删除，1=删除）", position = 99)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

}
