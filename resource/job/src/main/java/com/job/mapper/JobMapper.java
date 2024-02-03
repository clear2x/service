package com.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.job.entity.enums.JobStatusEnum;
import com.job.entity.mysql.Job;
import com.job.entity.param.JobPageQuery;
import com.job.entity.param.JobQuery;
import com.job.entity.vo.JobVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务 Mapper
 *
 * @author yuangy
 * @create 2020-08-20 11:51:01
 */
public interface JobMapper extends BaseMapper<Job> {

    IPage<JobVO> list(Page<?> page, @Param("query") JobPageQuery query);

    List<JobVO> list(@Param("query") JobQuery query);

    void updateStatus(@Param("ids") List<Long> ids, @Param("status") JobStatusEnum status);

}
