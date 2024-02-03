package com.bean.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuangy
 * @create 2020-07-14 16:43
 */
@Data
@Accessors(chain = true)
public class GeneratorColumnQuery {

    private String dataSource;
    private String tableName;
    private String schemaName;

}
