package com.bean.param;

import com.bean.param.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author yuangy
 * @create 2020-07-14 16:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel
public class GeneratorTableQuery extends BasePageQuery {

    @ApiModelProperty(value = "数据源", position = 1)
    private String dataSource;
    @ApiModelProperty(value = "数据库", position = 2)
    private String schemaName;
    @ApiModelProperty(value = "表格", position = 3)
    private String tableName;

}
