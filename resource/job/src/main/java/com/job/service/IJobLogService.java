package com.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.job.entity.mysql.JobLog;
import com.job.entity.param.JobLogPageQuery;
import com.job.entity.param.JobLogParam;
import com.job.entity.param.JobLogQuery;
import com.job.entity.vo.JobLogVO;

import java.util.List;

/**
 * 任务日志 Service接口
 *
 * @author yuangy
 * @create 2020-08-20 12:02:03
 */
public interface IJobLogService extends IService<JobLog> {

    /**
     * 查询（分页）
     *
     * @param query JobLogPageQuery
     * @return IPage<JobLogVO>
     */
    IPage<JobLogVO> page(JobLogPageQuery query);

    /**
     * 查询（列表）
     *
     * @param query JobLogQuery
     * @return List<JobLogVO>
     */
    List<JobLogVO> list(JobLogQuery query);

    /**
     * 新增/修改
     *
     * @param param JobLogParam
     */
    Boolean saveOrUpdate(JobLogParam param);

    /**
     * 删除
     *
     * @param ids List<Long>
     */
    Boolean delete(List<Long> ids);
}
