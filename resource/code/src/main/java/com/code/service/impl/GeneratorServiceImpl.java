package com.code.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bean.param.GeneratorColumnQuery;
import com.bean.param.GeneratorTableQuery;
import com.bean.vo.ColumnVO;
import com.bean.vo.TableVO;
import com.code.mapper.GeneratorMapper;
import com.code.service.IGeneratorService;
import com.datasource.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements IGeneratorService {

    private final GeneratorMapper generatorMapper;

    @Override
    @DS("#query.dataSource") // 动态切换数据源
    public IPage<TableVO> pageTables(GeneratorTableQuery query) {
        return generatorMapper.listTables(PageUtil.toPage(query), query);
    }

    @Override
    @DS("#query.dataSource") // 动态切换数据源
    public TableVO getTable(GeneratorTableQuery query) {
        List<TableVO> tableVOS = generatorMapper.listTables(query);
        return !CollectionUtils.isEmpty(tableVOS) ? tableVOS.get(0) : null;
    }

    @Override
    @DS("#query.dataSource") // 动态切换数据源
    public List<ColumnVO> listColumns(GeneratorColumnQuery query) {
        return generatorMapper.listColumns(query);
    }

}
