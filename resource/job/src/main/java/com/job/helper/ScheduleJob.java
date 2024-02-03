package com.job.helper;


import com.core.util.DateUtil;
import com.job.constant.JobConstant;
import com.job.entity.enums.JobResultEnum;
import com.job.entity.mysql.Job;
import com.job.entity.mysql.JobLog;
import com.job.service.IJobLogService;
import com.job.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.Future;

/**
 * 定时任务
 *
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        // 获取线程池
        ThreadPoolTaskExecutor scheduleJobExecutorService = SpringContextUtil.getBean(ThreadPoolTaskExecutor.class);
        // 任务
        Job scheduleJob = (Job) context.getMergedJobDataMap().get(JobConstant.JOB_PARAM_KEY);
        IJobLogService jobLogService = SpringContextUtil.getBean(IJobLogService.class);
        JobLog jobLog = new JobLog();
        jobLog.setJobId(scheduleJob.getId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setCreateTime(DateUtil.nowDateTime());

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            log.info("任务准备执行，任务ID：{}", scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = scheduleJobExecutorService.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes(times);
            jobLog.setResult(JobResultEnum.SUCCESS);
            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getId(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes(times);
            jobLog.setResult(JobResultEnum.FAIL);
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            jobLogService.save(jobLog);
        }
    }
}
