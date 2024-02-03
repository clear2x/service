package com.bean.param;

import com.bean.param.base.BasePageQuery;
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
public class GeneratorConfigQuery extends BasePageQuery {

    private Long id;
    private String dataSource;

}
