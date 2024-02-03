package com.code.controller;


import com.bean.param.GeneratorConfigParam;
import com.bean.param.GeneratorConfigQuery;
import com.bean.vo.GeneratorConfigVO;
import com.code.service.IGeneratorConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("config")
@Api(value = "代码生成配置", tags = "代码生成配置")
public class GeneratorConfigController {

    private final IGeneratorConfigService generatorConfigService;

    @ApiOperation(value = "获取配置")
    @GetMapping("/{dataSource}")
    public GeneratorConfigVO get(@PathVariable String dataSource) {
        return generatorConfigService.get(dataSource);
    }

    @ApiOperation(value = "获取所有配置")
    @GetMapping
    public List<GeneratorConfigVO> list() {
        return generatorConfigService.list(new GeneratorConfigQuery());
    }

    @ApiOperation(value = "新增/修改配置")
    @PutMapping
    public void saveOrUpdate(@Valid @RequestBody GeneratorConfigParam param) {
        this.generatorConfigService.saveOrUpdate(param);
    }

}
