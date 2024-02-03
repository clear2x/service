package com.job.entity.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.entity.mysql.Job;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;

/**
 * 任务 EntityParam
 *
 * @author yuangy
 * @create 2020-08-25 11:07:34
 */
@ApiModel
public class JobParam extends Job {

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
    public void setCreateBy(String createBy) {
        super.setCreateBy(createBy);
    }

    @Override
    @JsonIgnore
    public String getCreateBy() {
        return super.getCreateBy();
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

    @Override
    @JsonIgnore
    public void setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
    }

    @Override
    @JsonIgnore
    public String getUpdateBy() {
        return super.getUpdateBy();
    }

    @Override
    @JsonIgnore
    public void setUpdateTime(LocalDateTime updateTime) {
        super.setUpdateTime(updateTime);
    }

    @Override
    @JsonIgnore
    public LocalDateTime getUpdateTime() {
        return super.getUpdateTime();
    }


}
