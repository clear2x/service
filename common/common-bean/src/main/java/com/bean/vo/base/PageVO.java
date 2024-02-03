package com.bean.vo.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author yuangy
 * @create 2020-07-09 14:37
 */
@Data
@ApiModel(value = "分页响应结果", description = "分页响应结果")
public class PageVO {

    private Long total;

    private Object rows;

}
