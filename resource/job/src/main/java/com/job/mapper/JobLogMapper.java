package com.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.job.entity.mysql.JobLog;
import com.job.entity.param.JobLogPageQuery;
import com.job.entity.param.JobLogQuery;
import com.job.entity.vo.JobLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务日志 Mapper
 *
 * @author yuangy
 * @create 2020-08-20 12:02:03
 */
public interface JobLogMapper extends BaseMapper<JobLog> {

    IPage<JobLogVO> list(Page<?> page, @Param("query") JobLogPageQuery query);

    List<JobLogVO> list(@Param("query") JobLogQuery query);

}
