package com.bean.param.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yuangy
 * @create 2020-07-09 10:13
 */
@Data
@ApiModel(value = "分页请求参数", description = "分页请求参数")
public class BasePageQuery implements Serializable {

    /**
     * 当前页面数据量
     */
    @ApiModelProperty(value = "页面大小（默认10）")
    private int pageSize = 10;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页（默认1）")
    private int pageNum = 1;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    @ApiModelProperty(value = "排序规则：asc=升序，desc=降序")
    private String order;

}
