package com.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.job.entity.mysql.Job;
import com.job.entity.param.JobPageQuery;
import com.job.entity.param.JobParam;
import com.job.entity.param.JobQuery;
import com.job.entity.vo.JobVO;

import java.util.List;

/**
 * 任务 Service接口
 *
 * @author yuangy
 * @create 2020-08-20 11:51:01
 */
public interface IJobService extends IService<Job> {

    /**
     * 查询（分页）
     *
     * @param query JobPageQuery
     * @return IPage<JobVO>
     */
    IPage<JobVO> page(JobPageQuery query);

    /**
     * 查询（列表）
     *
     * @param query JobQuery
     * @return List<JobVO>
     */
    List<JobVO> list(JobQuery query);

    /**
     * 新增/修改
     *
     * @param param JobParam
     */
    Boolean saveOrUpdate(JobParam param);

    /**
     * 删除
     *
     * @param ids List<Long>
     */
    Boolean delete(List<Long> ids);

    /**
     * 运行定时任务
     *
     * @param ids 定时任务id
     */
    Boolean run(List<Long> ids);

    /**
     * 暂停定时任务
     *
     * @param ids 定时任务id
     */
    Boolean pause(List<Long> ids);

    /**
     * 恢复定时任务
     *
     * @param ids 定时任务id
     */
    Boolean resume(List<Long> ids);
}
