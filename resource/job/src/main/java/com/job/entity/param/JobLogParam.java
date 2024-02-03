package com.job.entity.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.entity.mysql.JobLog;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;

/**
 * 任务日志 EntityParam
 *
 * @author yuangy
 * @create 2020-08-25 11:13:06
 */
@ApiModel
public class JobLogParam extends JobLog {

    @Override
    @JsonIgnore
    public void setIsDeleted(Integer isDeleted) {
        super.setIsDeleted(isDeleted);
    }

    @Override
    @JsonIgnore
    public Integer getIsDeleted() {
        return super.getIsDeleted();
    }

    @Override
    @JsonIgnore
    public void setCreateTime(LocalDateTime createTime) {
        super.setCreateTime(createTime);
    }

    @Override
    @JsonIgnore
    public LocalDateTime getCreateTime() {
        return super.getCreateTime();
    }


}
