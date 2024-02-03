package ${basePackage}.${mapperPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${entityPackage}.${voName};
import ${basePackage}.${entityPackage}.${pageQueryName};
import ${basePackage}.${entityPackage}.${queryName};
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${tableComment} Mapper
 *
 * @author ${author}
 * @create ${date}
 */
public interface ${className}Mapper extends BaseMapper<${className}> {

    IPage<${voName}> list(Page<?> page, @Param("query") ${pageQueryName} query);

    List<${voName}> list(@Param("query") ${queryName} query);

}
