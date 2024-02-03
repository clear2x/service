package com.code.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.mysql.TestCode;
import com.bean.param.TestCodePageQuery;
import com.bean.param.TestCodeParam;
import com.bean.param.TestCodeQuery;
import com.bean.vo.TestCodeVO;
import com.code.mapper.TestCodeMapper;
import com.code.service.ITestCodeService;
import com.datasource.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 代码生成测试 Service实现
 *
 * @author yuangy
 * @create 2020-07-17 09:23:17
 */
@Service
public class TestCodeServiceImpl extends ServiceImpl<TestCodeMapper, TestCode> implements ITestCodeService {

    @Override
    public IPage<TestCodeVO> page(TestCodePageQuery query) {
        return this.baseMapper.list(PageUtil.toPage(query), query);
    }

    @Override
    public List<TestCodeVO> list(TestCodeQuery query) {
        return this.baseMapper.list(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(TestCodeParam param) {
        return super.saveOrUpdate(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        return this.removeByIds(ids);
    }
}
