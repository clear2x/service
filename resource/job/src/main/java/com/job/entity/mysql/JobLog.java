package com.job.entity.mysql;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.job.entity.enums.JobResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务日志 Entity
 *
 * @author yuangy
 * @create 2020-08-25 11:13:06
 */
@Data
@TableName("job_log")
@ApiModel
public class JobLog {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID", position = 2)
    @TableField("job_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /**
     * bean名称
     */
    @ApiModelProperty(value = "bean名称", position = 3)
    @TableField("bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", position = 4)
    @TableField("method_name")
    private String methodName;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数", position = 5)
    @TableField("params")
    private String params;

    /**
     * Job执行结果
     */
    @ApiModelProperty(value = "Job执行结果", position = 6)
    @TableField("result")
    private JobResultEnum result;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息", position = 7)
    @TableField("error")
    private String error;

    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时", position = 8)
    @TableField("times")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long times;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除", position = 9)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
