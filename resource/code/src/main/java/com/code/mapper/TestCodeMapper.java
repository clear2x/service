package com.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.mysql.TestCode;
import com.bean.param.TestCodePageQuery;
import com.bean.param.TestCodeQuery;
import com.bean.vo.TestCodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成测试 Mapper
 *
 * @author yuangy
 * @create 2020-07-17 09:23:17
 */
public interface TestCodeMapper extends BaseMapper<TestCode> {

    IPage<TestCodeVO> list(Page<?> page, @Param("query") TestCodePageQuery query);

    List<TestCodeVO> list(@Param("query") TestCodeQuery query);

}
