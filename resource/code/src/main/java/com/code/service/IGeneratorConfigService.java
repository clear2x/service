package com.code.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bean.mysql.GeneratorConfig;
import com.bean.param.GeneratorConfigQuery;
import com.bean.vo.GeneratorConfigVO;

import java.util.List;

public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfigVO get(String dataSource);

    List<GeneratorConfigVO> list(GeneratorConfigQuery generatorConfigQuery);
}
