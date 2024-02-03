package com.code.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bean.param.GeneratorColumnQuery;
import com.bean.param.GeneratorTableQuery;
import com.bean.vo.ColumnVO;
import com.bean.vo.TableVO;

import java.util.List;

public interface IGeneratorService {

    /**
     * 获取数据表
     *
     * @param query query
     * @return 数据表分页数据
     */
    IPage<TableVO> pageTables(GeneratorTableQuery query);

    /**
     * 获取数据表
     *
     * @param query query
     * @return 获取单个对象
     */
    TableVO getTable(GeneratorTableQuery query);

    /**
     * 获取数据表列信息
     *
     * @param query query
     * @return 数据表列信息
     */
    List<ColumnVO> listColumns(GeneratorColumnQuery query);

}
