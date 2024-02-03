package com.job.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.util.PageUtil;
import com.job.entity.mysql.JobLog;
import com.job.entity.param.JobLogPageQuery;
import com.job.entity.param.JobLogParam;
import com.job.entity.param.JobLogQuery;
import com.job.entity.vo.JobLogVO;
import com.job.mapper.JobLogMapper;
import com.job.service.IJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务日志 Service实现
 *
 * @author yuangy
 * @create 2020-08-20 12:02:03
 */
@Slf4j
@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

    @Override
    public IPage<JobLogVO> page(JobLogPageQuery query) {
        return this.baseMapper.list(PageUtil.toPage(query), query);
    }

    @Override
    public List<JobLogVO> list(JobLogQuery query) {
        return this.baseMapper.list(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(JobLogParam param) {
        return super.saveOrUpdate(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        return this.removeByIds(ids);
    }
}
