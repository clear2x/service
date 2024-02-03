package com.job.entity.param;

import com.bean.param.base.BasePageQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.job.entity.enums.JobStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 任务 EntityPageQuery
 *
 * @author yuangy
 * @create 2020-08-25 11:07:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel
public class JobPageQuery extends BasePageQuery {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * bean名称
     */
    @ApiModelProperty(value = "bean名称", position = 2)
    private String beanName;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", position = 3)
    private String methodName;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数", position = 4)
    private String params;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式", position = 5)
    private String cronExpression;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", position = 6)
    private JobStatusEnum status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 7)
    private String remark;

}
