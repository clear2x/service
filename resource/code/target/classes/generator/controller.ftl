package ${basePackage}.${controllerPackage};

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.core.entity.vo.base.PageVO;
import ${basePackage}.${servicePackage}.I${className}Service;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${entityPackage}.${voName};
import ${basePackage}.${entityPackage}.${pageQueryName};
import ${basePackage}.${entityPackage}.${queryName};
import ${basePackage}.${entityPackage}.${paramName};
import com.datasource.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.Map;

/**
 * ${tableComment} Controller
 *
 * @author ${author}
 * @create ${date}
 */
@Slf4j
@Validated
@RestController
@RequestMapping("${className?uncap_first}")
@RequiredArgsConstructor
@Api(value = "${tableComment}", tags = "${tableComment}")
public class ${className}Controller {

    private final I${className}Service ${className?uncap_first}Service;

    @ApiOperation(value = "单例")
    @GetMapping("/{id}")
    public ${voName} get(@PathVariable Long id) {
        return BeanUtil.shallowCopy(this.${className?uncap_first}Service.getById(id),${voName}.class);
    }

    @ApiOperation(value = "列表")
    @PostMapping("list")
    public List<${voName}> list(@RequestBody ${queryName} query) {
        return this.${className?uncap_first}Service.list(query);
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public PageVO page(@RequestBody ${pageQueryName} query) {
        return PageUtil.toPageVO(this.${className?uncap_first}Service.page(query));
    }

    @ApiOperation(value = "新增/修改")
    @PutMapping
    public Boolean saveOrUpdate(@RequestBody ${paramName} param) {
        return this.${className?uncap_first}Service.saveOrUpdate(param);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping
    public Boolean delete(@RequestBody List<Long> ids) {
        return this.${className?uncap_first}Service.delete(ids);
    }

}
