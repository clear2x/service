package ${basePackage}.${serviceImplPackage};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${entityPackage}.${voName};
import ${basePackage}.${entityPackage}.${pageQueryName};
import ${basePackage}.${entityPackage}.${queryName};
import ${basePackage}.${entityPackage}.${paramName};
import com.datasource.util.PageUtil;

import java.util.List;

/**
 * ${tableComment} Service实现
 *
 * @author ${author}
 * @create ${date}
 */
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements I${className}Service {

    @Override
    public IPage<${voName}> page(${pageQueryName} query) {
        return this.baseMapper.list(PageUtil.toPage(query), query);
    }

    @Override
    public List<${voName}> list(${queryName} query) {
        return this.baseMapper.list(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(${paramName} param) {
        return super.saveOrUpdate(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        return this.removeByIds(ids);
    }
}
