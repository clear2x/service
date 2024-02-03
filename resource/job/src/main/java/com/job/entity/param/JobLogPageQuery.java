package com.job.entity.param;

import com.bean.param.base.BasePageQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.job.entity.enums.JobResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 任务日志 EntityPageQuery
 *
 * @author yuangy
 * @create 2020-08-25 11:13:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel
public class JobLogPageQuery extends BasePageQuery {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID", position = 2)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /**
     * bean名称
     */
    @ApiModelProperty(value = "bean名称", position = 3)
    private String beanName;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称", position = 4)
    private String methodName;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数", position = 5)
    private String params;

    /**
     * Job执行结果
     */
    @ApiModelProperty(value = "Job执行结果", position = 6)
    private JobResultEnum result;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息", position = 7)
    private String error;

    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时", position = 8)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long times;

}
