package com.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.util.PageUtil;
import com.job.entity.enums.JobStatusEnum;
import com.job.entity.mysql.Job;
import com.job.entity.param.JobPageQuery;
import com.job.entity.param.JobParam;
import com.job.entity.param.JobQuery;
import com.job.entity.vo.JobVO;
import com.job.helper.ScheduleUtils;
import com.job.mapper.JobMapper;
import com.job.service.IJobService;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * 任务 Service实现
 *
 * @author yuangy
 * @create 2020-08-20 11:51:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
// 如果其他 bean 调用这个方法, 在其他 bean 中声明事务, 那就用事务. 如果其他 bean 没有声明事务, 那就不用事务.
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    private final Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        ScheduleUtils.deleteAllJob(scheduler);
        List<Job> scheduleJobList = this.baseMapper.selectList(new QueryWrapper<>());
        scheduleJobList.forEach(scheduleJob -> ScheduleUtils.createOrUpdateScheduleJob(scheduler, scheduleJob));
    }

    @Override
    public IPage<JobVO> page(JobPageQuery query) {
        IPage<JobVO> page = this.baseMapper.list(PageUtil.toPage(query), query);
        for (JobVO jobVO : page.getRecords()) {
            jobVO.setStatus(Optional.ofNullable(ScheduleUtils.getTriggerState(scheduler, jobVO.getId())).orElse(jobVO.getStatus()));
        }
        return page;
    }

    @Override
    public List<JobVO> list(JobQuery query) {
        List<JobVO> list = this.baseMapper.list(query);
        for (JobVO jobVO : list) {
            jobVO.setStatus(Optional.ofNullable(ScheduleUtils.getTriggerState(scheduler, jobVO.getId())).orElse(jobVO.getStatus()));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(JobParam param) {
        param.setStatus(JobStatusEnum.NORMAL);
        ScheduleUtils.createOrUpdateScheduleJob(scheduler, param);
        return super.saveOrUpdate(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        if (Collections.isEmpty(ids)) {
            return true;
        }
        ids.forEach(id -> ScheduleUtils.deleteScheduleJob(scheduler, id));
        return this.removeByIds(ids);
    }

    @Override
    public Boolean run(List<Long> ids) {
        if (Collections.isEmpty(ids)) {
            return true;
        }
        try {
            ids.forEach(id -> ScheduleUtils.run(scheduler, getById(id)));
        } catch (Exception e) {
            log.error("运行失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean pause(List<Long> ids) {
        if (Collections.isEmpty(ids)) {
            return true;
        }
        try {
            ids.forEach(id -> ScheduleUtils.pauseJob(scheduler, id));
            this.baseMapper.updateStatus(ids, JobStatusEnum.PAUSED);
        } catch (Exception e) {
            log.error("暂停失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean resume(List<Long> ids) {
        if (Collections.isEmpty(ids)) {
            return true;
        }
        try {
            ids.forEach(id -> ScheduleUtils.resumeJob(scheduler, id));
            this.baseMapper.updateStatus(ids, JobStatusEnum.NORMAL);
        } catch (Exception e) {
            log.error("恢复失败：", e);
            return false;
        }
        return true;
    }

}
