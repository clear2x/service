package com.code.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.mysql.GeneratorConfig;
import com.bean.param.GeneratorConfigQuery;
import com.bean.vo.GeneratorConfigVO;

import java.util.List;

public interface GeneratorConfigMapper extends BaseMapper<GeneratorConfig> {

    List<GeneratorConfigVO> list(GeneratorConfigQuery query);

}
