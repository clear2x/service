package com.bean.mysql.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字段
 *
 * @author yuangy
 * @create 2020-07-17 15:32
 */
@Data
public class CommonField {

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除（0=未删除，1=删除）", position = 99)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 99)
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 99)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", position = 99)
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", position = 99)
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
