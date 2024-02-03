package com.code.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.bean.mysql.GeneratorConfig;
import com.bean.param.GeneratorColumnQuery;
import com.bean.param.GeneratorQuery;
import com.bean.param.GeneratorTableQuery;
import com.bean.vo.ColumnVO;
import com.bean.vo.TableVO;
import com.bean.vo.base.PageVO;
import com.code.constant.GeneratorConstant;
import com.code.helper.GeneratorHelper;
import com.code.service.IGeneratorConfigService;
import com.code.service.IGeneratorService;
import com.core.exception.BaseException;
import com.core.util.BeanUtil;
import com.core.util.CamelCaseUtil;
import com.datasource.util.PageUtil;
import com.web.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Api(value = "代码生成", tags = "代码生成")
public class GeneratorController {

    private static final String SUFFIX = "_code.zip";

    private final IGeneratorService generatorService;
    private final IGeneratorConfigService generatorConfigService;
    private final GeneratorHelper generatorHelper;
    private final DynamicRoutingDataSource dynamicRoutingDataSource;

    @ApiOperation(value = "获取所有数据源")
    @GetMapping
    public Set<String> listDataSources() {
        return dynamicRoutingDataSource.getCurrentDataSources().keySet();
    }

    @ApiOperation(value = "获取所有表")
    @PostMapping("/listTables")
    public PageVO listTables(@RequestBody GeneratorTableQuery query) {
        return PageUtil.toPageVO(generatorService.pageTables(query));
    }

    @ApiOperation(value = "生成代码")
    @PostMapping("/generate")
    public void generate(@RequestBody @NotNull GeneratorQuery query, HttpServletResponse response) throws Exception {
        GeneratorConfig generatorConfig = generatorConfigService.get(query.getDataSource());
        if (generatorConfig == null) {
            throw new BaseException("代码生成配置为空");
        }

        TableVO table = generatorService.getTable(BeanUtil.shallowCopy(query, GeneratorTableQuery.class));
        if (table == null) {
            throw new BaseException("该表不存在");
        }

        String className = query.getTableName();
        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
            className = RegExUtils.replaceFirst(query.getTableName(), generatorConfig.getTrimValue(), StringUtils.EMPTY);
        }

        generatorConfig.setTableName(query.getTableName());
        generatorConfig.setClassName(CamelCaseUtil.underscoreToCapitalizeCamel(className));
        generatorConfig.setPageQueryName(generatorConfig.getClassName() + GeneratorConstant.PAGE_QUERY_TAG);
        generatorConfig.setQueryName(generatorConfig.getClassName() + GeneratorConstant.QUERY_TAG);
        generatorConfig.setParamName(generatorConfig.getClassName() + GeneratorConstant.PARAM_TAG);
        generatorConfig.setVoName(generatorConfig.getClassName() + GeneratorConstant.VO_TAG);
        generatorConfig.setTableComment(RegExUtils.replacePattern(table.getRemark(), "表$", ""));
        // 生成代码到临时目录
        List<ColumnVO> columns = generatorService.listColumns(BeanUtil.shallowCopy(query, GeneratorColumnQuery.class));
        // set field
        columns.forEach(item -> item.setField(CamelCaseUtil.underscoreToCamel(item.getName())));
        // entity
        generatorHelper.generateEntityFile(columns, generatorConfig);
        generatorHelper.generateEntityQueryFile(columns, generatorConfig);
        generatorHelper.generateEntityPageQueryFile(columns, generatorConfig);
        generatorHelper.generateEntityParamFile(columns, generatorConfig);
        generatorHelper.generateEntityVOFile(columns, generatorConfig);
        // mapper
        generatorHelper.generateMapperFile(columns, generatorConfig);
        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
        // service
        generatorHelper.generateServiceFile(columns, generatorConfig);
        generatorHelper.generateServiceImplFile(columns, generatorConfig);
        // controller
        generatorHelper.generateControllerFile(columns, generatorConfig);
        // 打包
        String zipFile = System.currentTimeMillis() + SUFFIX;
        FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);
        // 下载
        FileUtil.download(zipFile, query.getTableName() + SUFFIX, true, response);
        // 删除临时目录
        FileUtil.delete(GeneratorConstant.TEMP_PATH);
    }

}
