package ${basePackage}.${servicePackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${entityPackage}.${voName};
import ${basePackage}.${entityPackage}.${pageQueryName};
import ${basePackage}.${entityPackage}.${queryName};
import ${basePackage}.${entityPackage}.${paramName};

import java.util.List;

/**
 * ${tableComment} Service接口
 *
 * @author ${author}
 * @create ${date}
 */
public interface I${className}Service extends IService<${className}> {

    /**
     * 查询（分页）
     *
     * @param query ${pageQueryName}
     * @return IPage<${voName}>
     */
    IPage<${voName}> page(${pageQueryName} query);

    /**
     * 查询（列表）
     *
     * @param query ${queryName}
     * @return List<${voName}>
     */
    List<${voName}> list(${queryName} query);

    /**
     * 新增/修改
     *
     * @param param ${paramName}
     */
    Boolean saveOrUpdate(${paramName} param);

    /**
     * 删除
     *
     * @param ids List<Long>
     */
    Boolean delete(List<Long> ids);
}
