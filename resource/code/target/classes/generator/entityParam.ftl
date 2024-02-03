package ${basePackage}.${entityPackage};

<#if hasDate = true>
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ${tableComment} EntityParam
 *
 * @author ${author}
 * @create ${date}
 */
@ApiModel
public class ${paramName} extends ${className}{

<#list columns as column>
    @Override
    @JsonIgnore
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
    public void set${column.field?cap_first}(String ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public String get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    public void set${column.field?cap_first}(LocalDateTime ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public LocalDateTime get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    public void set${column.field?cap_first}(Integer ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public Integer get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'double'>
    public void set${column.field?cap_first}(Double ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public Double get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'bigint'>
    public void set${column.field?cap_first}(Long ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public Long get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'tinyint'>
    public void set${column.field?cap_first}(Byte ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public Byte get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>
    <#if column.type = 'decimal' || column.type = 'numeric'>
    public void set${column.field?cap_first}(BigDecimal ${column.field?uncap_first}) {
        super.set${column.field?cap_first}(${column.field?uncap_first});
    }

    @Override
    @JsonIgnore
    public BigDecimal get${column.field?cap_first}() {
        return super.get${column.field?cap_first}();
    }
    </#if>

</#list>

}
