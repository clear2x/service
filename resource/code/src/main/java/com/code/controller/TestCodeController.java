package com.code.controller;


import com.bean.param.TestCodePageQuery;
import com.bean.param.TestCodeParam;
import com.bean.param.TestCodeQuery;
import com.bean.vo.TestCodeVO;
import com.bean.vo.base.PageVO;
import com.code.service.ITestCodeService;
import com.core.util.BeanUtil;
import com.datasource.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码生成测试 Controller
 *
 * @author yuangy
 * @create 2020-07-17 10:31:52
 */
@Slf4j
@Validated
@RestController
@RequestMapping("testCode")
@RequiredArgsConstructor
@Api(value = "代码生成测试", tags = "代码生成测试")
public class TestCodeController {

    private final ITestCodeService testCodeService;

    @ApiOperation(value = "单例")
    @GetMapping("/{id}")
    public TestCodeVO get(@PathVariable Long id) {
        return BeanUtil.shallowCopy(this.testCodeService.getById(id), TestCodeVO.class);
    }

    @ApiOperation(value = "列表")
    @PostMapping("list")
    public List<TestCodeVO> list(@RequestBody TestCodeQuery query) {
        return this.testCodeService.list(query);
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public PageVO page(@RequestBody TestCodePageQuery query) {
        return PageUtil.toPageVO(this.testCodeService.page(query));
    }

    @ApiOperation(value = "新增/修改")
    @PutMapping
    public Boolean saveOrUpdate(@RequestBody TestCodeParam param) {
        return this.testCodeService.saveOrUpdate(param);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping
    public Boolean delete(@RequestBody List<Long> ids) {
        return this.testCodeService.delete(ids);
    }

}
