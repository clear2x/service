package com.code.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.mysql.GeneratorConfig;
import com.bean.param.GeneratorConfigQuery;
import com.bean.vo.GeneratorConfigVO;
import com.code.mapper.GeneratorConfigMapper;
import com.code.service.IGeneratorConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GeneratorConfigServiceImpl extends ServiceImpl<GeneratorConfigMapper, GeneratorConfig> implements IGeneratorConfigService {

    @Override
    public GeneratorConfigVO get(String dataSource) {
        List<GeneratorConfigVO> list = this.baseMapper.list(new GeneratorConfigQuery().setDataSource(dataSource));
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public List<GeneratorConfigVO> list(GeneratorConfigQuery query) {
        return this.baseMapper.list(query);
    }

}
