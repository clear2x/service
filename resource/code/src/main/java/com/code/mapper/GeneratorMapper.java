package com.code.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.param.GeneratorColumnQuery;
import com.bean.param.GeneratorTableQuery;
import com.bean.vo.ColumnVO;
import com.bean.vo.TableVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeneratorMapper {

    /**
     * 获取数据列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> listDatabases(@Param("databaseType") String databaseType);

    /**
     * 获取数据表
     *
     * @param page  page
     * @param query query
     * @return 数据表分页数据
     */
    IPage<TableVO> listTables(Page<?> page, @Param("query") GeneratorTableQuery query);

    List<TableVO> listTables(@Param("query") GeneratorTableQuery query);

    /**
     * 获取数据表列信息
     *
     * @param query query
     * @return 数据表列信息
     */
    List<ColumnVO> listColumns(GeneratorColumnQuery query);
}
