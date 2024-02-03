package com.job.controller;


import com.bean.vo.base.PageVO;
import com.core.util.BeanUtil;
import com.datasource.util.PageUtil;
import com.job.entity.param.JobLogPageQuery;
import com.job.entity.param.JobLogParam;
import com.job.entity.param.JobLogQuery;
import com.job.entity.vo.JobLogVO;
import com.job.service.IJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务日志 Controller
 *
 * @author yuangy
 * @create 2020-08-20 12:02:03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("jobLog")
@RequiredArgsConstructor
@Api(value = "任务日志", tags = "任务日志")
public class JobLogController {

    private final IJobLogService jobLogService;

    @ApiOperation(value = "单例")
    @GetMapping("/{id}")
    public JobLogVO get(@PathVariable Long id) {
        return BeanUtil.shallowCopy(this.jobLogService.getById(id),JobLogVO.class);
    }

    @ApiOperation(value = "列表")
    @PostMapping("list")
    public List<JobLogVO> list(@RequestBody JobLogQuery query) {
        return this.jobLogService.list(query);
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public PageVO page(@RequestBody JobLogPageQuery query) {
        return PageUtil.toPageVO(this.jobLogService.page(query));
    }

    @ApiOperation(value = "新增/修改")
    @PutMapping
    public Boolean saveOrUpdate(@RequestBody JobLogParam param) {
        return this.jobLogService.saveOrUpdate(param);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping
    public Boolean delete(@RequestBody List<Long> ids) {
        return this.jobLogService.delete(ids);
    }

}
