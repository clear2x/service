package com.job.entity.mysql;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.job.entity.enums.JobStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务 Entity
 *
 * @author yuangy
 * @create 2020-08-25 11:07:34
 */
@Data
@TableName("job")
@ApiModel
public class Job implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * bean名称
     */
    @ApiModelProperty(value = "bean名称", position = 2)
    @TableField("bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", position = 3)
    @TableField("method_name")
    private String methodName;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数", position = 4)
    @TableField("params")
    private String params;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式", position = 5)
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", position = 6)
    @TableField("status")
    private JobStatusEnum status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 7)
    @TableField("remark")
    private String remark;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除", position = 8)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 9)
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", position = 11)
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
