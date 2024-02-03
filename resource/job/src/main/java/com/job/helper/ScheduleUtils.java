package com.job.helper;

import com.job.constant.JobConstant;
import com.job.entity.enums.JobStatusEnum;
import com.job.entity.mysql.Job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务工具类
 */
@Slf4j
public class ScheduleUtils {

    private static final String JOB_NAME_PREFIX = "TASK_";

    /**
     * 获取触发器 key
     */
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error("获取Cron表达式失败", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(getJobKey(scheduleJob.getId()))
                    .build();
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(scheduleJob.getId()))
                    .withSchedule(scheduleBuilder).build();
            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(JobConstant.JOB_PARAM_KEY, scheduleJob);
            scheduler.scheduleJob(jobDetail, trigger);
            // 暂停任务
            if (scheduleJob.getStatus() == JobStatusEnum.PAUSED) {
                pauseJob(scheduler, scheduleJob.getId());
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());

            if (trigger == null) {
                throw new SchedulerException("获取Cron表达式失败");
            } else {
                // 按新的 cronExpression表达式重新构建 trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // 参数
                trigger.getJobDataMap().put(JobConstant.JOB_PARAM_KEY, scheduleJob);
            }
            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (scheduleJob.getStatus() == JobStatusEnum.PAUSED) {
                pauseJob(scheduler, scheduleJob.getId());
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * 创建或者更新
     */
    public static void createOrUpdateScheduleJob(Scheduler scheduler, Job scheduleJob) {
        CronTrigger cronTrigger = getCronTrigger(scheduler, scheduleJob.getId());
        if (cronTrigger == null) {
            createScheduleJob(scheduler, scheduleJob);
        } else {
            updateScheduleJob(scheduler, scheduleJob);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, Job scheduleJob) {
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JobConstant.JOB_PARAM_KEY, scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
        }
    }

    /**
     * 删除所有job
     */
    public static void deleteAllJob(Scheduler scheduler) {
        try {
            scheduler.deleteJobs(getAllJobs(scheduler));
        } catch (Exception e) {
            log.error("初始化-删除失败：", e);
        }
    }

    /**
     * 获取状态
     */
    public static JobStatusEnum getTriggerState(Scheduler scheduler, Long jobId) {
        try {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(getTriggerKey(jobId));
            return JobStatusEnum.instance(triggerState.name());
        } catch (Exception e) {
            log.error("获取状态失败：", e);
            return null;
        }
    }

    /**
     * 获取所有的JobKey
     */
    public static List<JobKey> getAllJobs(Scheduler scheduler) {
        List<JobKey> jobKeyList = new ArrayList<>();
        try {
            List<String> jobGroupNames = scheduler.getJobGroupNames();
            for (String groupName : jobGroupNames) {
                jobKeyList.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)));
            }

        } catch (Exception e) {
            log.error("获取所有任务失败：", e);
        }
        return jobKeyList;
    }

}
