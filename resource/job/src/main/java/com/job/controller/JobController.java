package com.job.controller;


import com.bean.vo.base.PageVO;
import com.core.util.BeanUtil;
import com.datasource.util.PageUtil;
import com.job.entity.param.JobPageQuery;
import com.job.entity.param.JobParam;
import com.job.entity.param.JobQuery;
import com.job.entity.vo.JobVO;
import com.job.service.IJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 任务 Controller
 *
 * @author yuangy
 * @create 2020-08-20 11:51:01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
@RequiredArgsConstructor
@Api(value = "任务", tags = "任务")
public class JobController {

    private final IJobService jobService;

    @ApiOperation(value = "单例")
    @GetMapping("/{id}")
    public JobVO get(@PathVariable Long id) {
        return BeanUtil.shallowCopy(this.jobService.getById(id), JobVO.class);
    }

    @ApiOperation(value = "列表")
    @PostMapping("list")
    public List<JobVO> list(@RequestBody JobQuery query) {
        return this.jobService.list(query);
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public PageVO page(@RequestBody JobPageQuery query) {
        return PageUtil.toPageVO(this.jobService.page(query));
    }

    @ApiOperation(value = "运行")
    @PostMapping("run")
    public Boolean run(@NotEmpty(message = "{required}") @RequestBody List<Long> ids) {
        return this.jobService.run(ids);
    }

    @ApiOperation(value = "暂停")
    @PostMapping("pause")
    public Boolean pause(@NotEmpty(message = "{required}") @RequestBody List<Long> ids) {
        return this.jobService.pause(ids);
    }

    @ApiOperation(value = "恢复")
    @PostMapping("resume")
    public Boolean resume(@NotEmpty(message = "{required}") @RequestBody List<Long> ids) {
        return this.jobService.resume(ids);
    }

    @ApiOperation(value = "新增/修改")
    @PutMapping
    public Boolean saveOrUpdate(@Validated @RequestBody JobParam param) {
        return this.jobService.saveOrUpdate(param);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping
    public Boolean delete(@NotEmpty(message = "{required}") @RequestBody List<Long> ids) {
        return this.jobService.delete(ids);
    }

}
