package com.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bean.mysql.TestCode;
import com.bean.param.TestCodePageQuery;
import com.bean.param.TestCodeParam;
import com.bean.param.TestCodeQuery;
import com.bean.vo.TestCodeVO;

import java.util.List;

/**
 * 代码生成测试 Service接口
 *
 * @author yuangy
 * @create 2020-07-17 09:23:17
 */
public interface ITestCodeService extends IService<TestCode> {

    /**
     * 查询（分页）
     *
     * @param query TestCodePageQuery
     * @return IPage<TestCodeVO>
     */
    IPage<TestCodeVO> page(TestCodePageQuery query);

    /**
     * 查询（列表）
     *
     * @param query TestCodeQuery
     * @return List<TestCodeVO>
     */
    List<TestCodeVO> list(TestCodeQuery query);

    /**
     * 新增/修改
     *
     * @param param TestCodeParam
     */
    Boolean saveOrUpdate(TestCodeParam param);

    /**
     * 删除
     *
     * @param ids List<Long>
     */
    Boolean delete(List<Long> ids);
}
